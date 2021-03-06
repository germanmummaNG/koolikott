package ee.hm.dop.service.useractions;

import ee.hm.dop.dao.AuthenticatedUserDao;
import ee.hm.dop.model.AuthenticatedUser;
import ee.hm.dop.model.User;
import ee.hm.dop.model.ehis.Person;
import ee.hm.dop.model.enums.LoginFrom;
import ee.hm.dop.model.user.UserSession;
import ee.hm.dop.service.login.TokenGenerator;
import ee.hm.dop.utils.exceptions.DuplicateTokenException;
import ee.hm.dop.config.Configuration;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.time.LocalDateTime;

import static ee.hm.dop.utils.ConfigurationProperties.SESSION_DURATION_MINS;
import static java.time.LocalDateTime.now;

@Service
@Transactional
public class SessionService {

    @Inject
    private AuthenticatedUserDao authenticatedUserDao;
    @Inject
    private TokenGenerator tokenGenerator;
    @Inject
    private Configuration configuration;

    public AuthenticatedUser startSession(User user, Person person, LoginFrom loginFrom) {
        LocalDateTime now = now();
        return startSession(new AuthenticatedUser(user, person, loginFrom, now, sessionTime(now)));
    }

    public AuthenticatedUser updateSession(UserSession userSession, AuthenticatedUser authenticatedUser) {
        if (userSession.isContinueSession()) {
            return prolongSession(authenticatedUser);
        }
        return declineSession(authenticatedUser);
    }

    public void terminateSession(AuthenticatedUser authenticatedUser) {
        if (authenticatedUser != null) {
            authenticatedUser.setSessionTime(LocalDateTime.now());
            authenticatedUserDao.delete(authenticatedUser);
        }
    }

    public void logout(AuthenticatedUser authenticatedUser) {
        if (authenticatedUser != null) {
            authenticatedUser.setSessionTime(LocalDateTime.now());
            authenticatedUser.setLoggedOut(true);
            authenticatedUserDao.delete(authenticatedUser);
        }
    }

    private AuthenticatedUser prolongSession(AuthenticatedUser authenticatedUser) {
        authenticatedUser.setSessionTime(sessionTime(LocalDateTime.now()));
        authenticatedUser.setSessionNumber(authenticatedUser.getSessionNumber() != null ? authenticatedUser.getSessionNumber() + 1 : 1);
        return authenticatedUserDao.createOrUpdate(authenticatedUser);
    }

    private AuthenticatedUser declineSession(AuthenticatedUser authenticatedUser) {
        authenticatedUser.setDeclined(true);
        return authenticatedUserDao.createOrUpdate(authenticatedUser);
    }

    private LocalDateTime sessionTime(LocalDateTime loginDate) {
        return loginDate.plusMinutes(configuration.getInt(SESSION_DURATION_MINS));
    }

    private AuthenticatedUser startSession(AuthenticatedUser authenticatedUser) {
        try {
            authenticatedUser.setToken(tokenGenerator.secureToken());
            return authenticatedUserDao.createAuthenticatedUser(authenticatedUser);
        } catch (DuplicateTokenException e) {
            authenticatedUser.setToken(tokenGenerator.secureToken());
            return authenticatedUserDao.createAuthenticatedUser(authenticatedUser);
        }
    }
}
