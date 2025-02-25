package realtyhub.user.service.impl;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import realtyhub.common.service.CodeSenderService;
import realtyhub.common.service.EmailSenderService;
import realtyhub.common.service.PasswordEncryptorService;
import realtyhub.user.model.dto.request.UserEmailVerificationRequest;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;
import realtyhub.user.service.UserEmailVerificationService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserEmailVerificationServiceImpl implements UserEmailVerificationService {

    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final VerificationRepository verificationRepository;
    private final PasswordEncryptorService passwordEncryptorService;

    @Override
    public void sendEmailVerification(
            final UserEmailVerificationRequest userEmailVerificationRequest
    ) {
        try {
            if (userRepository.existsUserEntityByEmail(userEmailVerificationRequest.getEmail()) || verificationRepository.existsByEmail(userEmailVerificationRequest.getEmail())) {
                throw new RuntimeException("User email already exists");
            }

            final String code = String.valueOf(CodeSenderService.sendCode());
            emailSenderService.sendEmail(userEmailVerificationRequest.getEmail(), "User Confirm.(CODE)", "Code for user confirmation => " + code + "\nPlease account confirmation in 5 minute.");

            VerificationCode verificationCode = VerificationCode.builder()
                    .code(passwordEncryptorService.encryptPassword(code))
                    .email(userEmailVerificationRequest.getEmail())
                    .expiryDate(LocalDateTime.now().plusMinutes(5)).build();
            verificationRepository.save(verificationCode);
        }catch (OptimisticLockException e){
            throw new RuntimeException("Verification conflict with another user email verification!!!");
        }

    }

}
