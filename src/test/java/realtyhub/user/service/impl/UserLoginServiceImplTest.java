package realtyhub.user.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.common.util.PasswordEncryptorUtil;
import realtyhub.user.model.dto.request.UserLoginRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserLoginServiceImplTest {

    @InjectMocks
    private UserLoginServiceImpl userLoginServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptorUtil passwordEncryptorUtil;

    @Test
    void shouldLoginUserWithValidCredentials() {
        final String email = "email@email.com";

        final String rawPassword = "password";
        final String encryptedPassword = "hashedPassword";
        Mockito.when(passwordEncryptorUtil.encryptPassword(rawPassword)).thenReturn(encryptedPassword);
        final String passwordResult = passwordEncryptorUtil.encryptPassword(rawPassword);

        final UserLoginRequest loginRequest = UserLoginRequest.builder()
                .email(email)
                .password(rawPassword)
                .build();

        final UserEntity user = UserEntity.builder()
                .email(loginRequest.getEmail())
                .password(encryptedPassword)
                .lastPasswordChange(LocalDate.now().plusDays(1))
                .build();

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncryptorUtil.isMatch(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        userLoginServiceImpl.login(loginRequest);

        Assert.assertEquals(encryptedPassword, passwordResult);
        Assert.assertNotEquals(user.getLastPasswordChange(), LocalDateTime.now());

        Mockito.verify(passwordEncryptorUtil, Mockito.times(1)).encryptPassword(rawPassword);
        Mockito.verify(userRepository).findByEmail(user.getEmail());
        Mockito.verify(passwordEncryptorUtil).isMatch(loginRequest.getPassword(), user.getPassword());
    }
}