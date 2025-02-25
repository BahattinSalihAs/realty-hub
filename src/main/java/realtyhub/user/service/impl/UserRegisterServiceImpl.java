package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.user.controller.RealtorController;
import realtyhub.user.model.dto.request.UserRegisterRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;
import realtyhub.user.service.UserRegisterService;
import realtyhub.common.service.PasswordEncryptorService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class UserRegisterServiceImpl implements UserRegisterService {

    private final UserRepository userRepository;
    private final PasswordEncryptorService passwordEncryptorService;
    private final VerificationRepository verificationRepository;
    public static final int MAX_REALTORS = 3;
    public static final int MAX_ADMINS = 1;

    @Override
    public void registerUser(
            final UserRegisterRequest userRegisterRequest,
            final UserRole userRole
            ) {

        if (userRole == UserRole.REALTOR){
            final long realtorsCount = userRepository.countUserEntityByUserRole(UserRole.REALTOR);
            if (realtorsCount >= MAX_REALTORS){
                throw new RuntimeException("Realtors count exceeds max allowed");
            }
            if (!userRegisterRequest.getCodeAdmin().equals(RealtorController.adminCode)){
                throw new RuntimeException("Admin code does not match");
            }
        }
        if (userRole == UserRole.ADMIN){
            long adminsCount = userRepository.countUserEntityByUserRole(UserRole.ADMIN);
            if (adminsCount >= MAX_ADMINS){
                throw new RuntimeException("Admins count exceeds max allowed");
            }
        }

        VerificationCode verificationCode = verificationRepository.findByEmail(userRegisterRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email address"));

        if (!passwordEncryptorService.matches(userRegisterRequest.getCode(), verificationCode.getCode()) || LocalDateTime.now().isAfter(verificationCode.getExpiryDate())) {
            throw new IllegalArgumentException("Invalid verification code or admin code");
        }

        if (userRepository.existsUserEntityByPhoneNumber(userRegisterRequest.getPhoneNumber())){
            throw new RuntimeException("User phone number already exists");
        }

        final UserEntity userEntityToBeRegistered = UserEntity.builder()
                .name(userRegisterRequest.getName())
                .surname(userRegisterRequest.getSurname())
                .email(userRegisterRequest.getEmail())
                .age(userRegisterRequest.getAge())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .lastPasswordChange(LocalDate.now().plusDays(180))
                .password(passwordEncryptorService.encryptPassword(userRegisterRequest.getPassword()))
                .userRole(userRole).build();

        userRepository.save(userEntityToBeRegistered);

    }
}
