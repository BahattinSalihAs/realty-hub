package realtyhub.user.service.impl;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import realtyhub.common.error.UserNotFoundException;
import realtyhub.common.util.CodeSenderUtil;
import realtyhub.common.util.EmailSenderUtil;
import realtyhub.user.model.dto.request.UserForgotPasswordRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;
import realtyhub.user.service.UserForgotPasswordService;
import realtyhub.common.util.PasswordEncryptorUtil;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class UserForgotPasswordServiceImpl implements UserForgotPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncryptorUtil passwordEncryptorUtil;
    private final EmailSenderUtil emailSenderService;
    private final VerificationRepository verificationRepository;

    @Override
    public void forgotPassword(
            final UserForgotPasswordRequest userForgotPasswordRequest
    ) {
        UserEntity userEntityFromDB = userRepository.findByEmail(userForgotPasswordRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        try {
            final String code = String.valueOf(CodeSenderUtil.sendCode());

            userEntityFromDB.setOtpCode(passwordEncryptorUtil.encryptPassword(code));
            userRepository.save(userEntityFromDB);

            emailSenderService.sendEmail(userEntityFromDB.getEmail(),"Password Reset(OTP CODE)", "Otp code for password change => " + code + "\nPlease 5 minute in your password change...");

            final VerificationCode verificationCode = VerificationCode.builder()
                    .email(userEntityFromDB.getEmail())
                    .code(passwordEncryptorUtil.encryptPassword(code))
                    .expiryDate(LocalDateTime.now().plusMinutes(5))
                    .build();
            verificationRepository.save(verificationCode);
        }catch (OptimisticLockException e){
            throw new RuntimeException("Otp code conflicted with another user otp code!!!");
        }


    }
}
