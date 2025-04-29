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
import realtyhub.user.model.dto.request.UserEmailVerificationRequest;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserEmailVerificationServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    EmailSenderUtil emailSenderUtil;

    @Mock
    VerificationRepository verificationRepository;

    @Mock
    PasswordEncryptorUtil passwordEncryptorUtil;

    @InjectMocks
    UserEmailVerificationServiceImpl userEmailVerificationServiceImpl;

    @Test
    public void shouldSendVerificationEmailAndSaveVerificationCode(){
        final String code = "12345";
        final String encryptedCode = "hashedCode";

        try (MockedStatic<CodeSenderUtil> mocked = Mockito.mockStatic(CodeSenderUtil.class)) {
            mocked.when(CodeSenderUtil::sendCode).thenReturn(Integer.valueOf(code));

        }

        Mockito.when(passwordEncryptorUtil.encryptPassword(code)).thenReturn(encryptedCode);

        final String resultCode = passwordEncryptorUtil.encryptPassword(code);

        Assert.assertEquals(resultCode, encryptedCode);

        Mockito.verify(passwordEncryptorUtil).encryptPassword(code);

        UserEmailVerificationRequest userEmailVerificationRequest = UserEmailVerificationRequest.builder()
                .email("email@email.com")
                .build();

        Mockito.when(userRepository.existsUserEntityByEmail(userEmailVerificationRequest.getEmail())).thenReturn(false);
        Mockito.when(verificationRepository.existsByEmail(userEmailVerificationRequest.getEmail())).thenReturn(false);

        VerificationCode verificationCode = VerificationCode.builder()
                .code(encryptedCode)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .email(userEmailVerificationRequest.getEmail())
                .build();

        Mockito.when(verificationRepository.save(Mockito.any(VerificationCode.class))).thenReturn(verificationCode);

        Mockito.doNothing().when(emailSenderUtil).sendEmail(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        );

        userEmailVerificationServiceImpl.sendEmailVerification(userEmailVerificationRequest);

        Mockito.verify(emailSenderUtil).sendEmail(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        );

        Mockito.verify(verificationRepository).save(Mockito.any(VerificationCode.class));

    }

}