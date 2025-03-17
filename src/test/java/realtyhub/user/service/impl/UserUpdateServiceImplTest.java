package realtyhub.user.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.user.model.dto.request.UserUpdateRequest;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserUpdateServiceImplTest {

    @InjectMocks
    private UserUpdateServiceImpl userUpdateServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldUpdateUserSuccessfully() {
        final String email = "test@realtyhub.com";

        final UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .email(email)
                .name("TEST MAN")
                .phoneNumber("123456789")
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .email(userUpdateRequest.getEmail())
                .name(userUpdateRequest.getName())
                .phoneNumber(userUpdateRequest.getPhoneNumber())
                .build();

        Mockito.when(userRepository.findByEmail(userUpdateRequest.getEmail())).thenReturn(Optional.of(userEntity));
        Mockito.when(userRepository.existsUserEntityByPhoneNumber(userUpdateRequest.getPhoneNumber())).thenReturn(false);
        Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);

        userUpdateServiceImpl.updateUser(userUpdateRequest);

        Mockito.verify(userRepository).findByEmail(userUpdateRequest.getEmail());
        Mockito.verify(userRepository).save(userEntity);
        Mockito.verify(userRepository).existsUserEntityByPhoneNumber(Mockito.anyString());
    }
}