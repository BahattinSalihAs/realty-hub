package realtyhub.user.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import realtyhub.common.security.service.JwtService;
import realtyhub.common.util.PasswordEncryptorUtil;
import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.dto.request.UserRegisterRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRegisterServiceImplTest {

    @InjectMocks
    private UserRegisterServiceImpl userRegisterServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptorUtil passwordEncryptorUtil;

    @Mock
    private VerificationRepository verificationRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Test
    public void shouldSuccessfullyRegisterCustomerWhenValidRequestIsProvider() {
        final String rawPassword = "password";
        final String encryptedPassword = "hashedPassword";
        Mockito.when(passwordEncryptorUtil.encryptPassword(rawPassword)).thenReturn(encryptedPassword);
        final String passwordResult = passwordEncryptorUtil.encryptPassword(rawPassword);

        final String rawCode = "rawCode";
        final String encryptedCode = "hashedCode";
        Mockito.when(passwordEncryptorUtil.encryptPassword(rawCode)).thenReturn(encryptedCode);
        final String codeResult = passwordEncryptorUtil.encryptPassword(rawCode);

        final String email = "test@realtyhub.com";
        VerificationCode verificationCode = VerificationCode.builder()
                .code(encryptedCode)
                .email(email)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        final UserRole userRole = UserRole.CUSTOMER;

        // customer for register
        final UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .email(email)
                .password(encryptedPassword)
                .age(40)
                .name("name")
                .surname("surname")
                .phoneNumber("05553336558")
                .code(rawCode)
                .build();

        final UserEntity customer = UserEntity.builder()
                .name(userRegisterRequest.getName())
                .surname(userRegisterRequest.getSurname())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .email(userRegisterRequest.getEmail())
                .password(encryptedPassword)
                .age(userRegisterRequest.getAge())
                .userRole(userRole)
                .lastPasswordChange(LocalDate.now().plusDays(180))
                .confirmCode(null)
                .build();

        final String token = jwtService.generateToken(customer);


        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(customer);
        Mockito.when(verificationRepository.findByEmail(verificationCode.getEmail())).thenReturn(Optional.of(verificationCode));
        Mockito.when(passwordEncryptorUtil.isMatch(rawCode, encryptedCode)).thenReturn(true);
        Mockito.when(userRepository.existsUserEntityByPhoneNumber(Mockito.anyString())).thenReturn(false);
        Mockito.doNothing().when(verificationRepository).delete(verificationCode);
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRegisterRequest.getEmail(), userRegisterRequest.getPassword())))
                .thenReturn(new UsernamePasswordAuthenticationToken(userRegisterRequest.getEmail(), userRegisterRequest.getPassword()));

        final UserResponse result = userRegisterServiceImpl.registerUser(userRegisterRequest, userRole);

        //asserts
        Assert.assertEquals(token, result.getToken());
        Assert.assertEquals(encryptedPassword, passwordResult);
        Assert.assertEquals(codeResult, encryptedCode);
        Assert.assertNotNull(result);

        //verify
        Mockito.verify(userRepository).save(Mockito.any(UserEntity.class));
        Mockito.verify(verificationRepository).findByEmail(verificationCode.getEmail());
        Mockito.verify(passwordEncryptorUtil).isMatch(rawCode, encryptedCode);
        Mockito.verify(passwordEncryptorUtil).encryptPassword(rawCode);
        Mockito.verify(passwordEncryptorUtil).encryptPassword(rawPassword);
        Mockito.verify(userRepository).existsUserEntityByPhoneNumber(Mockito.anyString());
        Mockito.verify(verificationRepository).delete(verificationCode);
        Mockito.verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

    }
}