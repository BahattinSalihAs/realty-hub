package realtyhub.user.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import realtyhub.common.security.service.JwtService;
import realtyhub.common.util.PasswordEncryptorUtil;
import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.dto.request.UserAuthenticationRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAuthenticationServiceImplTest {

    @InjectMocks
    private UserAuthenticationServiceImpl userAuthenticationServiceImpl;

    @Mock
    private PasswordEncryptorUtil passwordEncryptorUtil;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Test
    final void shouldAuthenticateUserWithValidCredentials() {
        final String email = "test@test.com";

        final String rawPassword = "password";
        final String encryptedPassword = "hashedPassword";
        Mockito.when(passwordEncryptorUtil.encryptPassword(rawPassword)).thenReturn(encryptedPassword);
        final String passwordResult = passwordEncryptorUtil.encryptPassword(rawPassword);

        final UserAuthenticationRequest userAuthenticationRequest = UserAuthenticationRequest.builder()
                .email(email)
                .password(rawPassword)
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .email(userAuthenticationRequest.getEmail())
                .password(encryptedPassword)
                .build();

        Mockito.when(userRepository.findByEmail(userAuthenticationRequest.getEmail())).thenReturn(Optional.of(userEntity));
        Mockito.when(passwordEncryptorUtil.isMatch(userAuthenticationRequest.getPassword(), userEntity.getPassword())).thenReturn(true);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getEmail(), userAuthenticationRequest.getPassword())))
                .thenReturn(new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getEmail(), userAuthenticationRequest.getPassword()));

        final String token = jwtService.generateToken(userEntity);
        final UserResponse result = userAuthenticationServiceImpl.authenticate(userAuthenticationRequest);

        Assert.assertEquals(token, result.getToken());
        Assert.assertEquals(encryptedPassword, passwordResult);
        Assert.assertNotNull(result);

        Mockito.verify(passwordEncryptorUtil).encryptPassword(rawPassword);
        Mockito.verify(userRepository).findByEmail(userAuthenticationRequest.getEmail());
        Mockito.verify(passwordEncryptorUtil).isMatch(userAuthenticationRequest.getPassword(), userEntity.getPassword());
        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

    }

}