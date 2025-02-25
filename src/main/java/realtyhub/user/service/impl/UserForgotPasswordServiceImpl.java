package realtyhub.user.service.impl;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import realtyhub.common.service.CodeSenderService;
import realtyhub.common.service.EmailSenderService;
import realtyhub.user.model.dto.request.UserForgotPasswordRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;
import realtyhub.user.service.UserForgotPasswordService;
import realtyhub.common.service.PasswordEncryptorService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class UserForgotPasswordServiceImpl implements UserForgotPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncryptorService passwordEncryptorService;
    private final EmailSenderService emailSenderService;
    private final VerificationRepository verificationRepository;

    @Override
    public void forgotPassword(
            final UserForgotPasswordRequest userForgotPasswordRequest
    ) {
        UserEntity userEntityFromDB = userRepository.findByEmail(userForgotPasswordRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            final String code = String.valueOf(CodeSenderService.sendCode());

            userEntityFromDB.setOtpCode(passwordEncryptorService.encryptPassword(code));
            userRepository.save(userEntityFromDB);

            emailSenderService.sendEmail(userEntityFromDB.getEmail(),"Password Reset(OTP CODE)", "Otp code for password change => " + code + "\nPlease 5 minute in your password change...");

            final VerificationCode verificationCode = VerificationCode.builder()
                    .email(userEntityFromDB.getEmail())
                    .code(passwordEncryptorService.encryptPassword(code))
                    .expiryDate(LocalDateTime.now().plusMinutes(5))
                    .build();
            verificationRepository.save(verificationCode);
        }catch (OptimisticLockException e){
            throw new RuntimeException("Otp code conflicted with another user otp code!!!");
        }


    }
}
