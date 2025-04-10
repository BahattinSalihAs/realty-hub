package realtyhub.user.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import realtyhub.common.security.service.JwtService;
import realtyhub.user.model.dto.request.UserRegisterRequest;
import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.model.entity.VerificationCode;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.repository.VerificationRepository;
import realtyhub.user.service.UserRegisterService;
import realtyhub.common.util.PasswordEncryptorUtil;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
class UserRegisterServiceImpl implements UserRegisterService {

    private final UserRepository userRepository;
    private final PasswordEncryptorUtil passwordEncryptorUtil;
    private final VerificationRepository verificationRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public static final int MAX_REALTOR_COUNT = 3;
    public static final int MAX_ADMIN_COUNT = 1;

    @Transactional
    @Override
    public UserResponse registerUser(
            final UserRegisterRequest userRegisterRequest,
            final UserRole userRole
            ) {

        if (userRole == UserRole.REALTOR){
            final long realtorsCount = userRepository.countUserEntityByUserRole(UserRole.REALTOR);
            if (realtorsCount >= MAX_REALTOR_COUNT){
                throw new RuntimeException("Realtors count exceeds max allowed");
            }
        }
        if (userRole == UserRole.ADMIN){
            long adminsCount = userRepository.countUserEntityByUserRole(UserRole.ADMIN);
            if (adminsCount >= MAX_ADMIN_COUNT){
                throw new RuntimeException("Admins count exceeds max allowed");
            }
        }

        VerificationCode verificationCode = verificationRepository.findByEmail(userRegisterRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email address"));

        if (!passwordEncryptorUtil.isMatch(userRegisterRequest.getCode(), verificationCode.getCode()) || LocalDateTime.now().isAfter(verificationCode.getExpiryDate())) {
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
                .password(passwordEncryptorUtil.encryptPassword(userRegisterRequest.getPassword()))
                .confirmCode(null)
                .userRole(userRole).build();

        userRepository.save(userEntityToBeRegistered);
        verificationRepository.delete(verificationCode);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRegisterRequest.getEmail(),
                userRegisterRequest.getPassword()));
        final String token = jwtService.generateToken(userEntityToBeRegistered);

        return UserResponse.builder().token(token).build();
    }
}
