package realtyhub.user.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.common.util.PasswordEncryptorUtil;
import realtyhub.user.model.dto.request.UserPasswordChangeRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserPasswordChangeServiceImplTest {

    @InjectMocks
    private UserPasswordChangeServiceImpl userPasswordChangeServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncryptorUtil passwordEncryptorUtil;

    @Mock
    private VerificationRepository verificationRepository;

    @Test
    void shouldChangePasswordWithValidData() {
        final String email = "test@test.com";

        final String rawCode = "rawCode";
        final String encryptedCode = "hashedCode";
        Mockito.when(passwordEncryptorUtil.encryptPassword(rawCode)).thenReturn(encryptedCode);
        final String codeResult = passwordEncryptorUtil.encryptPassword(rawCode);

        final String newPassword = "new password";
        final String encryptedPassword = "hashedPassword";
        Mockito.when(passwordEncryptorUtil.encryptPassword(newPassword)).thenReturn(encryptedPassword);
        final String passwordResult = passwordEncryptorUtil.encryptPassword(newPassword);

        final UserPasswordChangeRequest userPasswordChangeRequest = UserPasswordChangeRequest.builder()
                .email(email)
                .newPassword(newPassword)
                .otpCode(rawCode)
                .build();

        final VerificationCode verif = VerificationCode.builder()
                .code(encryptedCode)
                .email(email)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .email(userPasswordChangeRequest.getEmail())
                .password(encryptedPassword)
                .otpCode(encryptedCode)
                .build();

        Mockito.when(userRepository.findByEmail(userPasswordChangeRequest.getEmail())).thenReturn(Optional.of(userEntity));
        Mockito.when(verificationRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(verif));
        Mockito.when(passwordEncryptorUtil.isMatch(userPasswordChangeRequest.getOtpCode(), userEntity.getOtpCode())).thenReturn(true);
        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);

        userPasswordChangeServiceImpl.changePassword(userPasswordChangeRequest);

        Assert.assertNotEquals(verif.getExpiryDate(), LocalDateTime.now());
        Assert.assertEquals(encryptedPassword, passwordResult);
        Assert.assertEquals(encryptedCode, codeResult);

        Mockito.verify(passwordEncryptorUtil, Mockito.times(2)).encryptPassword(newPassword);
        Mockito.verify(passwordEncryptorUtil).encryptPassword(rawCode);
        Mockito.verify(userRepository).findByEmail(userPasswordChangeRequest.getEmail());
        Mockito.verify(verificationRepository).findByEmail(userEntity.getEmail());
        Mockito.verify(passwordEncryptorUtil).isMatch(userPasswordChangeRequest.getOtpCode(), userEntity.getOtpCode());
        Mockito.verify(userRepository).save(userEntity);


    }
}