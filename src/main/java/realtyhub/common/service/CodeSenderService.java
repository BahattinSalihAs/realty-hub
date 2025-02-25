package realtyhub.common.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeSenderService {

    public static final int sendCode(){
        final GoogleAuthenticatorConfig conf = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder().build();
        final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator(conf);
        final String secret = googleAuthenticator.createCredentials().getKey();
        return googleAuthenticator.getTotpPassword(secret);
    }

}
