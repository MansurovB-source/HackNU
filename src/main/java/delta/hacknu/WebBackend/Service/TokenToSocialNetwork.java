package delta.hacknu.WebBackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Behruz Mansurov
 */

@Service
public class TokenToSocialNetwork {
    PasswordEncoder passwordEncoder;

    @Autowired
    public TokenToSocialNetwork(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String getTokenFromUsernameAndPassword(String unprocessedPassword) {
       return passwordEncoder.encode(unprocessedPassword + "we're the winners");
    }

    public boolean checkTokens(String token_1, String token_2) {
        return passwordEncoder.matches(token_1, token_2);
    }
}
