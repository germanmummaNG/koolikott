package ee.hm.dop.service.login;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
@Transactional
public class TokenGenerator {

    private SecureRandom random = new SecureRandom();

    public String secureToken() {
        return new BigInteger(130, random).toString(32);
    }
}
