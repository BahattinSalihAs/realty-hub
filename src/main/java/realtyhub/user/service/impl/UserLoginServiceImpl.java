package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.common.error.UserNotFoundException;
import realtyhub.user.model.dto.request.UserLoginRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.service.UserLoginService;
import realtyhub.common.util.PasswordEncryptorUtil;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncryptorUtil passwordEncryptorUtil;

    @Override
    public final void login(
            final UserLoginRequest userLoginRequest
    ) {

        final UserEntity userEntityFromDB = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncryptorUtil.isMatch(userLoginRequest.getPassword(), userEntityFromDB.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (userEntityFromDB.getLastPasswordChange().equals(LocalDate.now())){
            throw new RuntimeException("Please change your password 180 day finished!");
        }

    }

}
