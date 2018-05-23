package ee.hm.dop.service.login;

import ee.hm.dop.dao.AgreementDao;
import ee.hm.dop.dao.AuthenticationStateDao;
import ee.hm.dop.dao.UserAgreementDao;
import ee.hm.dop.model.*;
import ee.hm.dop.model.ehis.Person;
import ee.hm.dop.service.ehis.IEhisSOAPService;
import ee.hm.dop.service.login.dto.UserStatus;
import ee.hm.dop.service.useractions.AuthenticatedUserService;
import ee.hm.dop.service.useractions.UserService;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;

import static ee.hm.dop.service.login.dto.UserStatus.loggedIn;
import static ee.hm.dop.service.login.dto.UserStatus.missingPermissionsExistingUser;
import static ee.hm.dop.service.login.dto.UserStatus.missingPermissionsNewUser;
import static java.lang.String.format;

public class LoginService {
    private static final int MILLISECONDS_AUTHENTICATIONSTATE_IS_VALID_FOR = 5 * 60 * 1000;

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Inject
    private UserService userService;
    @Inject
    private AuthenticationStateService authenticationStateService;
    @Inject
    private AuthenticatedUserService authenticatedUserService;
    @Inject
    private AuthenticationStateDao authenticationStateDao;
    @Inject
    private IEhisSOAPService ehisSOAPService;
    @Inject
    private AgreementDao agreementDao;
    @Inject
    private UserAgreementDao userAgreementDao;

    public UserStatus login(String idCode, String name, String surname) {
        Agreement latestAgreement = agreementDao.findLatestAgreement();
        if (latestAgreement == null) {
            return loggedIn(finalizeLogin(idCode, name, surname));
        }
        User user = userService.getUserByIdCode(idCode);
        if (user == null) {
            AuthenticationState state = authenticationStateService.save(idCode, name, surname);
            return missingPermissionsNewUser(state.getToken(), latestAgreement.getId());
        }
        if (userAgreementDao.agreementDoesntExist(user.getId(), latestAgreement.getId())) {
            AuthenticationState state = authenticationStateService.save(idCode, name, surname);
            logger.info(format("User with id %s doesn't have agreement", user.getId()));
            return missingPermissionsExistingUser(state.getToken(), latestAgreement.getId());
        }

        logger.info(format("User with id %s logged in", user.getId()));
        return loggedIn(authenticate(user));
    }

    public UserStatus login(AuthenticationState authenticationState) {
        if (authenticationState == null) {
            return null;
        }

        if (hasExpired(authenticationState)) {
            authenticationStateDao.delete(authenticationState);
            return null;
        }

        UserStatus authenticatedUser = login(authenticationState.getIdCode(), authenticationState.getName(), authenticationState.getSurname());
        authenticationStateDao.delete(authenticationState);
        return authenticatedUser;
    }

    public AuthenticatedUser finalizeLogin(UserStatus userStatus) {
        AuthenticationState state = authenticationStateDao.findAuthenticationStateByToken(userStatus.getToken());
        if (state == null) {
            return null;
        }

        if (hasExpired(state)) {
            authenticationStateDao.delete(state);
            return null;
        }
        if (userStatus.getAgreementId() == null) {
            throw new RuntimeException("No agreement for token: " + userStatus.getToken());
        }

        User user = getExistingOrNewUser(state);
        Agreement agreement = agreementDao.findById(userStatus.getAgreementId());
        if (userAgreementDao.agreementDoesntExist(user.getId(), agreement.getId())) {
            userAgreementDao.createOrUpdate(createUserAgreement(user, agreement, true));
        }

        AuthenticatedUser authenticate = authenticate(user);
        authenticationStateDao.delete(state);

        logger.info(format("User with id %s finalized login and logged in", user.getId()));
        return authenticate;
    }

    public void rejectAgreement(UserStatus userStatus) {
        AuthenticationState state = authenticationStateDao.findAuthenticationStateByToken(userStatus.getToken());
        if (state == null) {
            return;
        }

        if (hasExpired(state)) {
            authenticationStateDao.delete(state);
            return;
        }
        if (userStatus.getAgreementId() == null) {
            throw new RuntimeException("No agreement for token: " + userStatus.getToken());
        }
        User user = userService.getUserByIdCode(state.getIdCode());
        if (user == null) {
            return;
        }
        Agreement agreement = agreementDao.findById(userStatus.getAgreementId());
        if (userAgreementDao.agreementDoesntExist(user.getId(), agreement.getId())) {
            userAgreementDao.createOrUpdate(createUserAgreement(user, agreement, false));
        }
    }

    private AuthenticatedUser finalizeLogin(String idCode, String name, String surname) {
        return authenticate(getExistingOrNewUser(idCode, name, surname));
    }

    private AuthenticatedUser authenticate(User user) {
        Person person = ehisSOAPService.getPersonInformation(user.getIdCode());
        return authenticatedUserService.save(new AuthenticatedUser(user, person));
    }

    private User getExistingOrNewUser(AuthenticationState state) {
        return getExistingOrNewUser(state.getIdCode(), state.getName(), state.getSurname());
    }

    private User getExistingOrNewUser(String idCode, String firstname, String surname) {
        User existingUser = userService.getUserByIdCode(idCode);
        if (existingUser != null) {
            return existingUser;
        }
        userService.create(idCode, firstname, surname);
        User newUser = userService.getUserByIdCode(idCode);
        if (newUser == null) {
            throw new RuntimeException(format("User with id %s tried to log in after creating account, but failed.", idCode));
        }
        logger.info("System created new user with id %s", newUser.getId());
        newUser.setNewUser(true);
        if (newUser.getUserAgreements() == null) {
            newUser.setUserAgreements(new ArrayList<>());
        }
        return newUser;
    }

    private boolean hasExpired(AuthenticationState state) {
        Interval interval = new Interval(state.getCreated(), new DateTime());
        Duration duration = new Duration(MILLISECONDS_AUTHENTICATIONSTATE_IS_VALID_FOR);
        return interval.toDuration().isLongerThan(duration);
    }

    private User_Agreement createUserAgreement(User user, Agreement agreement, boolean agreed) {
        User_Agreement userAgreement = new User_Agreement();
        userAgreement.setUser(user);
        userAgreement.setAgreement(agreement);
        userAgreement.setAgreed(agreed);
        userAgreement.setCreatedAt(DateTime.now());
        return userAgreement;
    }
}
