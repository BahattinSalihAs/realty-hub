package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.common.error.UserNotFoundException;
import realtyhub.user.model.dto.request.UserUpdateRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.service.UserUpdateService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
final class UserUpdateServiceImpl implements UserUpdateService {

    private final UserRepository userRepository;


    @Override
    public final void updateUser(
            final UserUpdateRequest userUpdateRequest
    ) {
        UserEntity userEntityFromDB = userRepository.findByEmail(userUpdateRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (userRepository.existsUserEntityByPhoneNumber(userUpdateRequest.getPhoneNumber())){
            throw new RuntimeException("User phone number already exists");
        }

        if (userUpdateRequest.getAge() > 0){
            userEntityFromDB.setAge(userUpdateRequest.getAge());
        }

        if (userUpdateRequest.getPhoneNumber() != null){
            userEntityFromDB.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }

        if (userUpdateRequest.getName() != null){
            userEntityFromDB.setName(userUpdateRequest.getName());
        }

        if (userUpdateRequest.getSurname() != null){
            userEntityFromDB.setSurname(userUpdateRequest.getSurname());
        }

        userRepository.save(userEntityFromDB);
    }
}
