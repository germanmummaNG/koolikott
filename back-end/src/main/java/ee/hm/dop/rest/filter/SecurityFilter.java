package ee.hm.dop.rest.filter;

import ee.hm.dop.model.AuthenticatedUser;
import ee.hm.dop.rest.filter.dto.DopPrincipal;
import ee.hm.dop.rest.filter.dto.DopSecurityContext;
import ee.hm.dop.service.login.SessionUtil;
import ee.hm.dop.service.useractions.AuthenticatedUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static ee.hm.dop.config.guice.GuiceInjector.getInjector;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    public static final int HTTP_AUTHENTICATION_TIMEOUT = 419;
    public Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String token = requestContext.getHeaderString("Authentication");

        if (token != null) {
            AuthenticatedUser authenticatedUser = authenticatedUserService().getAuthenticatedUserByToken(token);
            if (authenticatedUser == null) {
                userHasAlreadyLoggedOut(requestContext);
                return;
            }
            String username = authenticatedUser.getUser().getUsername();
            if (!username.equals(requestContext.getHeaderString("Username"))) {
                requestHeaderAndUsernameDontMatch(requestContext, username);
                return;
            }
            if (SessionUtil.sessionInValid(authenticatedUser)) {
                sessionExpired(requestContext, username);
                return;
            }

            DopPrincipal principal = new DopPrincipal(authenticatedUser);
            DopSecurityContext securityContext = new DopSecurityContext(principal, requestContext.getUriInfo());
            requestContext.setSecurityContext(securityContext);
        }
    }

    private void sessionExpired(ContainerRequestContext requestContext, String username) {
        requestContext.abortWith(Response.status(HTTP_AUTHENTICATION_TIMEOUT).build());
        logger.error("session has expired" + username);
    }

    private void requestHeaderAndUsernameDontMatch(ContainerRequestContext requestContext, String username) {
        requestContext.abortWith(Response.status(HTTP_AUTHENTICATION_TIMEOUT).build());
        logger.error("user request header and username do not match: " + username);
    }

    private void userHasAlreadyLoggedOut(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(HTTP_AUTHENTICATION_TIMEOUT).build());
        logger.error("user has already logged out");
    }

    protected AuthenticatedUserService authenticatedUserService() {
        return getInjector().getInstance(AuthenticatedUserService.class);
    }
}
