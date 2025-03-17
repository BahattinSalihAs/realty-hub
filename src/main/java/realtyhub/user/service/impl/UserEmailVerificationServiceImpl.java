package realtyhub.user.service.impl;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import realtyhub.common.util.CodeSenderUtil;
import realtyhub.common.util.EmailSenderUtil;
import realtyhub.common.util.PasswordEncryptorUtil;
import realtyhub.user.model.dto.request.UserEmailVerificationRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;
import realtyhub.user.service.UserEmailVerificationService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class UserEmailVerificationServiceImpl implements UserEmailVerificationService {

    private final UserRepository userRepository;
    private final EmailSenderUtil emailSenderService;
    private final VerificationRepository verificationRepository;
    private final PasswordEncryptorUtil passwordEncryptorUtil;
    public static String adminCode;
    public static String verifyCode;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredVerifications() {
        LocalDateTime now = LocalDateTime.now();
        verificationRepository.deleteByExpiryDateBefore(now);
    }

    @Override
    public void sendEmailVerification(
            final UserEmailVerificationRequest userEmailVerificationRequest
    ) {
        if (userRepository.existsUserEntityByEmail(userEmailVerificationRequest.getEmail()) || verificationRepository.existsByEmail(userEmailVerificationRequest.getEmail())) {
            throw new RuntimeException("User email already exists");
        }

        try {
            final String code = String.valueOf(CodeSenderUtil.sendCode());
            emailSenderService.sendEmail(userEmailVerificationRequest.getEmail(), "User Confirm.(CODE)", "Code for user confirmation => " + code + "\nPlease account confirmation in 5 minute.");

            VerificationCode verificationCode = VerificationCode.builder()
                    .code(passwordEncryptorUtil.encryptPassword(code))
                    .email(userEmailVerificationRequest.getEmail())
                    .expiryDate(LocalDateTime.now().plusMinutes(5)).build();
            verificationRepository.save(verificationCode);
        }catch (OptimisticLockException e){
            throw new RuntimeException("Verification conflict with another user email verification!!!");
        }
    }

    @Override
    public void sendVerificationCodeRealtorForAdminApproval(
            final String email
    ) {
        UserEntity userEntityFromDB = userRepository.findUserEntityByUserRole(UserRole.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        adminCode = String.valueOf(CodeSenderUtil.sendCode());
        emailSenderService.sendEmail(userEntityFromDB.getEmail(), "VERIFY CODE REALTOR", "Code: " + adminCode + " \nfor " + email + " send if safe user!");

    }

    @Override
    public void sendVerificationCodeAdminForApproval(String email) {
        verifyCode = String.valueOf(CodeSenderUtil.sendCode());
        emailSenderService.sendEmail("b.s.a@outlook.com.tr", "VERIFY CODE ADMIN", "Code: " + verifyCode + " \nfor " + email + " send if safe admin!");
    }

}
