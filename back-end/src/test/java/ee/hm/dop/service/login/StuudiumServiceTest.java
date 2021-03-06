package ee.hm.dop.service.login;

import ee.hm.dop.model.AuthenticatedUser;
import ee.hm.dop.model.enums.LoginFrom;
import ee.hm.dop.model.stuudium.StuudiumUser;
import ee.hm.dop.service.login.dto.UserStatus;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.configuration2.Configuration;
import org.easymock.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static ee.hm.dop.utils.ConfigurationProperties.STUUDIUM_CLIENT_ID;
import static ee.hm.dop.utils.ConfigurationProperties.STUUDIUM_EXTRA_LOGGING;
import static ee.hm.dop.utils.ConfigurationProperties.STUUDIUM_URL_GENERALDATA;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

@RunWith(EasyMockRunner.class)
public class StuudiumServiceTest {

    @TestSubject
    private StuudiumService stuudiumService = new StuudiumService();
    @Mock
    private Configuration configuration;
    @Mock
    private Client client;
    @Mock
    private WebTarget target;
    @Mock
    private Builder builder;
    @Mock
    private LoginService loginService;

    @Before
    public void setUp() throws Exception {
        String clientSecret = "super-secret";
        stuudiumService.postConstruct(clientSecret);
    }

    @Test
    public void authenticate() {
        String token = "123456";

        String generalDataURL = "https://www.example.com/generaldata";
        String clientID = "test-client-id";
        String clientSecret = "super-secret";

        StuudiumUser stuudiumUser = new StuudiumUser();
        stuudiumUser.setIdCode("1116669990");
        stuudiumUser.setFirstName("Firsttest");
        stuudiumUser.setLastName("Secondtest");

        Response response = createMock(Response.class);
        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        UserStatus userStatus = UserStatus.loggedIn(authenticatedUser);

        expect(configuration.getBoolean(STUUDIUM_EXTRA_LOGGING)).andReturn(false);
        expect(configuration.getString(STUUDIUM_URL_GENERALDATA)).andReturn(generalDataURL);
        expect(configuration.getString(STUUDIUM_CLIENT_ID)).andReturn(clientID);

        expect(client.target(generalDataURL)).andReturn(target);
        expect(target.queryParam("token", token)).andReturn(target);
        expect(target.queryParam("client_id", clientID)).andReturn(target);
        expect(target.queryParam("signature", HmacUtils.hmacSha1Hex(clientSecret, token))).andReturn(target);
        expect(target.request()).andReturn(builder);
        expect(builder.accept(MediaType.APPLICATION_JSON)).andReturn(builder);
        expect(builder.get()).andReturn(response);

        expect(response.readEntity(StuudiumUser.class)).andReturn(stuudiumUser);

        expect(loginService.login(stuudiumUser.getIdCode(), stuudiumUser.getFirstName(), stuudiumUser.getLastName(), LoginFrom.STUUDIUM)) //
                .andReturn(userStatus);

        replayAll(response);

        UserStatus returnedAuthenticatedUser = stuudiumService.authenticate(token);

        verifyAll(response);

        assertNotNull(returnedAuthenticatedUser);
        assertSame(userStatus, returnedAuthenticatedUser);
    }

    private void replayAll(Object... mocks) {
        replay(configuration, loginService, client, target, builder);

        if (mocks != null) {
            for (Object object : mocks) {
                replay(object);
            }
        }
    }

    private void verifyAll(Object... mocks) {
        verify(configuration, loginService, client, target, builder);

        if (mocks != null) {
            for (Object object : mocks) {
                verify(object);
            }
        }
    }

}
