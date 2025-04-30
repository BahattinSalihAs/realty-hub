package realtyhub.user.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.common.util.CodeSenderUtil;
import realtyhub.common.util.EmailSenderUtil;
import realtyhub.common.util.PasswordEncryptorUtil;
import realtyhub.user.model.dto.request.UserForgotPasswordRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserForgotPasswordServiceImplTest {

    @InjectMocks
    private UserForgotPasswordServiceImpl userForgotPasswordServiceImpl;

    @Mock
    private PasswordEncryptorUtil passwordEncryptorUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationRepository verificationRepository;

    @Mock
    private EmailSenderUtil emailSenderUtil;

    @Test
    void shouldSendEmailWithOtpCodeForForgotPassword() {
        final String code = "12345";
        try (MockedStatic<CodeSenderUtil> mocked = Mockito.mockStatic(CodeSenderUtil.class)) {
            mocked.when(CodeSenderUtil::sendCode).thenReturn(Integer.valueOf(code));

        }

        final String encryptedCode = "hashedCode";
        Mockito.when(passwordEncryptorUtil.encryptPassword(code)).thenReturn(encryptedCode);
        final String codeResult = passwordEncryptorUtil.encryptPassword(code);

        final String email = "test@realtyhub.com";

        final UserForgotPasswordRequest userForgotPasswordRequest = UserForgotPasswordRequest.builder()
                .email(email)
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .email(userForgotPasswordRequest.getEmail())
                .otpCode(encryptedCode)
                .build();

        final VerificationCode verif = VerificationCode.builder()
                .email(userEntity.getEmail())
                .code(encryptedCode)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        Mockito.when(verificationRepository.save(Mockito.any(VerificationCode.class))).thenReturn(verif);
        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);
        Mockito.doNothing().when(emailSenderUtil).sendEmail(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        );
        Mockito.when(userRepository.findByEmail(userForgotPasswordRequest.getEmail())).thenReturn(Optional.of(userEntity));

        userForgotPasswordServiceImpl.forgotPassword(userForgotPasswordRequest);

        Assert.assertEquals(codeResult, encryptedCode);

        Mockito.verify(verificationRepository).save(Mockito.any(VerificationCode.class));
        Mockito.verify(userRepository).save(userEntity);
        Mockito.verify(emailSenderUtil).sendEmail(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        );

    }
}