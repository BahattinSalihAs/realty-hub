package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.common.error.UserNotFoundException;
import realtyhub.user.model.dto.request.UserPasswordChangeRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;
import realtyhub.user.service.UserPasswordChangeService;
import realtyhub.common.util.PasswordEncryptorUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserPasswordChangeServiceImpl implements UserPasswordChangeService {

    private final UserRepository userRepository;
    private final PasswordEncryptorUtil passwordEncryptorUtil;
    private final VerificationRepository verificationRepository;

    @Override
    public void changePassword(
            final UserPasswordChangeRequest userPasswordChangeRequest
    ) {

        UserEntity userEntityFromDB = userRepository.findByEmail(userPasswordChangeRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        VerificationCode verificationCode = verificationRepository.findByEmail(userEntityFromDB.getEmail())
                .orElseThrow(() -> new RuntimeException("Code not found"));

        if (!passwordEncryptorUtil.isMatch(userPasswordChangeRequest.getOtpCode(), userEntityFromDB.getOtpCode()) || LocalDateTime.now().isAfter(verificationCode.getExpiryDate())){
            throw new RuntimeException("OTP code mismatch");
        }

        userEntityFromDB.setPassword(passwordEncryptorUtil.encryptPassword(userPasswordChangeRequest.getNewPassword()));

        userEntityFromDB.setLastPasswordChange(LocalDate.now().plusDays(180));

        userRepository.save(userEntityFromDB);

    }
}
