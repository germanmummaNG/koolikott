package ee.hm.dop.rest.login;

import ee.hm.dop.model.AuthenticatedUser;
import ee.hm.dop.model.mobileid.MobileIDSecurityCodes;
import ee.hm.dop.rest.BaseResource;
import ee.hm.dop.service.login.*;
import ee.hm.dop.service.login.dto.UserStatus;
import ee.hm.dop.service.metadata.LanguageService;
import ee.hm.dop.service.useractions.AuthenticatedUserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.soap.SOAPException;
import java.net.URI;
import java.net.URISyntaxException;

import static ee.hm.dop.rest.login.IdCardUtil.*;
import static java.lang.String.format;

@Path("login")
public class LoginResource extends BaseResource {

    private static final String EKOOL_CALLBACK_PATH = "/rest/login/ekool/success";
    private static final String EKOOL_AUTHENTICATION_URL = "%s?client_id=%s&redirect_uri=%s&scope=read&response_type=code";
    private static final String STUUDIUM_AUTHENTICATION_URL = "%sclient_id=%s";
    public static final String LOGIN_REDIRECT_WITH_TOKEN_AGREEMENT = "%s/#!/loginRedirect?token=%s&agreement=%s&existingUser=%s";
    public static final String LOGIN_REDIRECT_WITH_TOKEN = "%s/#!/loginRedirect?token=%s";
    public static final String LOGIN_REDIRECT_WITHOUT_TOKEN = "%s/#!/loginRedirect";

    @Inject
    private LoginService loginService;
    @Inject
    private EkoolService ekoolService;
    @Inject
    private StuudiumService stuudiumService;
    @Inject
    private AuthenticatedUserService authenticatedUserService;
    @Inject
    private LanguageService languageService;
    @Inject
    private MobileIDLoginService mobileIDLoginService;

    @POST
    @Path("/finalizeLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthenticatedUser permissionConfirm(UserStatus userStatus) {
        return confirmed(userStatus) ? loginService.finalizeLogin(userStatus) : null;
    }

    @POST
    @Path("/rejectAgreement")
    @Produces(MediaType.APPLICATION_JSON)
    public void permissionReject(UserStatus userStatus) {
        if (userStatus.isExistingUser()) {
            loginService.rejectAgreement(userStatus);
        }
    }

    @GET
    @Path("/idCard")
    @Produces(MediaType.APPLICATION_JSON)
    public UserStatus idCardLogin() {
        HttpServletRequest req = getRequest();
        return isAuthValid(req) ? loginService.login(getIdCode(req), getName(req), getSurname(req)) : null;
    }

    @GET
    @Path("ekool")
    public Response ekoolAuthenticate() throws URISyntaxException {
        return redirect(getEkoolAuthenticationURI());
    }

    @GET
    @Path("ekool/success")
    public Response ekoolAuthenticateSuccess(@QueryParam("code") String code) throws URISyntaxException {
        return redirect(getEkoolLocation(code));
    }

    @GET
    @Path("stuudium")
    public Response stuudiumAuthenticate(@QueryParam("token") String token) throws URISyntaxException {
        return token != null ? authenticateWithStuudiumToken(token) : redirectToStuudium();
    }

    @GET
    @Path("/mobileId")
    @Produces(MediaType.APPLICATION_JSON)
    public MobileIDSecurityCodes mobileIDLogin(@QueryParam("phoneNumber") String phoneNumber,
                                               @QueryParam("idCode") String idCode,
                                               @QueryParam("language") String languageCode) throws Exception {
        return mobileIDLoginService.authenticate(phoneNumber, idCode, languageService.getLanguage(languageCode));
    }

    @GET
    @Path("/mobileId/isValid")
    @Produces(MediaType.APPLICATION_JSON)
    public UserStatus mobileIDAuthenticate(@QueryParam("token") String token) throws SOAPException {
        return mobileIDLoginService.validateMobileIDAuthentication(token);
    }

    @GET
    @Path("/getAuthenticatedUser")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthenticatedUser getAuthenticatedUser(@QueryParam("token") String token) {
        return authenticatedUserService.getAuthenticatedUserByToken(token);
    }

    private Response redirectToStuudium() throws URISyntaxException {
        return redirect(getStuudiumAuthenticationURI());
    }

    private Response authenticateWithStuudiumToken(String token) throws URISyntaxException {
        return redirect(getStuudiumLocation(token));
    }

    private URI getEkoolAuthenticationURI() throws URISyntaxException {
        return new URI(format(EKOOL_AUTHENTICATION_URL, ekoolService.getAuthorizationUrl(), ekoolService.getClientId(), getEkoolCallbackUrl()));
    }

    private URI getStuudiumAuthenticationURI() throws URISyntaxException {
        return new URI(format(STUUDIUM_AUTHENTICATION_URL, stuudiumService.getAuthorizationUrl(), stuudiumService.getClientId()));
    }

    private String getEkoolCallbackUrl() {
        return getServerAddress() + EKOOL_CALLBACK_PATH;
    }

    private URI getStuudiumLocation(String token) throws URISyntaxException {
        try {
            return redirectSuccess(stuudiumService.authenticate(token));
        } catch (Exception e) {
            return redirectFailure();
        }
    }

    private URI getEkoolLocation(String code) throws URISyntaxException {
        try {
            return redirectSuccess(ekoolService.authenticate(code, getEkoolCallbackUrl()));
        } catch (Exception e) {
            return redirectFailure();
        }
    }

    private URI redirectSuccess(UserStatus status) throws URISyntaxException {
        if (status.isStatusOk()) {
            return new URI(format(LOGIN_REDIRECT_WITH_TOKEN, getServerAddress(), status.getAuthenticatedUser().getToken()));
        }
        return new URI(format(LOGIN_REDIRECT_WITH_TOKEN_AGREEMENT, getServerAddress(), status.getToken(), status.getAgreementId().toString(), status.isExistingUser()));
    }

    private URI redirectFailure() throws URISyntaxException {
        return new URI(format(LOGIN_REDIRECT_WITHOUT_TOKEN, getServerAddress()));
    }

    private boolean confirmed(UserStatus userStatus) {
        return userStatus != null && userStatus.isUserConfirmed();
    }
}