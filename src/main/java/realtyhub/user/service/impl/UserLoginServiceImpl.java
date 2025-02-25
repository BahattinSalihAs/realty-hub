package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.user.model.dto.request.UserLoginRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.service.UserLoginService;
import realtyhub.common.service.PasswordEncryptorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class UserLoginServiceImpl implements UserLoginService {
    private final UserRepository userRepository;
    private final PasswordEncryptorService passwordEncryptorService;

    @Override
    public void login(
            final UserLoginRequest userLoginRequest
    ) {

        UserEntity userEntityFromDB = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncryptorService.matches(userLoginRequest.getPassword(), userEntityFromDB.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (userEntityFromDB.getLastPasswordChange().equals(LocalDate.now())){
            throw new RuntimeException("Please change your password 180 day finished!");
        }

    }
}
