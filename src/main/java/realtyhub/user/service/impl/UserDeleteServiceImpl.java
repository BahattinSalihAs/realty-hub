package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.common.error.UserNotFoundException;
import realtyhub.user.model.dto.request.UserDeleteRequest;
import realtyhub.user.model.entity.FavoriteEntity;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.repository.FavoriteRepository;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.service.UserDeleteService;
import realtyhub.common.util.PasswordEncryptorUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserDeleteServiceImpl implements UserDeleteService {

    private final UserRepository userRepository;
    private final PasswordEncryptorUtil passwordEncryptorUtil;
    private final FavoriteRepository favoriteRepository;


    @Override
    public void deleteUser(
            final UserDeleteRequest userDeleteRequest
    ) {
        final UserEntity userEntityFromDB = userRepository.findByEmail(userDeleteRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncryptorUtil.isMatch(userDeleteRequest.getPassword(), userEntityFromDB.getPassword())){
            throw new RuntimeException("Password does not match");
        }

        if (userEntityFromDB.getUserRole() == UserRole.CUSTOMER) {
            final FavoriteEntity fav = favoriteRepository.findByUserEntity(userEntityFromDB)
                    .orElseThrow(() -> new RuntimeException("User favorite not found"));
            favoriteRepository.delete(fav);

        }
        userRepository.delete(userEntityFromDB);
    }
}
