package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.user.model.dto.request.UserDeleteRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.service.UserDeleteService;
import realtyhub.common.service.PasswordEncryptorService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserDeleteServiceImpl implements UserDeleteService {

    private final UserRepository userRepository;
    private final PasswordEncryptorService passwordEncryptorService;


    @Override
    public void deleteUser(
            final UserDeleteRequest userDeleteRequest
    ) {
        final UserEntity userEntityFromDB = userRepository.findByEmail(userDeleteRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncryptorService.matches(userDeleteRequest.getPassword(), userEntityFromDB.getPassword())){
            throw new RuntimeException("Password does not match");
        }

        userRepository.delete(userEntityFromDB);
    }
}
