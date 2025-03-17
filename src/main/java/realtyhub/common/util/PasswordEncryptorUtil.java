package realtyhub.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncryptorUtil {

    public String encryptPassword(
            final String rawPassword
    ) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean isMatch(
            final String rawPassword,
            final String hashedPassword
    ){
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

}
