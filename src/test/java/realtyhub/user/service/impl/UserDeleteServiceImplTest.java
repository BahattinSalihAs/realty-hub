package realtyhub.user.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.common.util.PasswordEncryptorUtil;
import realtyhub.user.model.dto.request.UserDeleteRequest;
import realtyhub.user.model.entity.FavoriteEntity;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.repository.FavoriteRepository;
import realtyhub.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDeleteServiceImplTest {

    @InjectMocks
    private UserDeleteServiceImpl userDeleteServiceImpl;

    @Mock
    private PasswordEncryptorUtil passwordEncryptorUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Test
    void shouldDeleteUserSuccessfully() {
        final String email = "test@realtyhub.com";

        final String rawPassword = "password";
        final String encryptedPassword = "hashedPassword";
        Mockito.when(passwordEncryptorUtil.encryptPassword(rawPassword)).thenReturn(encryptedPassword);
        final String passwordResult = passwordEncryptorUtil.encryptPassword(rawPassword);

        final UserDeleteRequest userDeleteRequest = UserDeleteRequest.builder()
                .email(email)
                .password(rawPassword)
                .build();

        final UserEntity userEntity = UserEntity.builder()
                .email(userDeleteRequest.getEmail())
                .password(encryptedPassword)
                .build();

        Mockito.when(userRepository.findByEmail(userDeleteRequest.getEmail())).thenReturn(Optional.of(userEntity));
        Mockito.when(passwordEncryptorUtil.isMatch(userDeleteRequest.getPassword(), userEntity.getPassword())).thenReturn(true);

        final FavoriteEntity fav = FavoriteEntity.builder()
                .userEntity(userEntity)
                .id(1234L)
                .build();
        Mockito.when(favoriteRepository.findByUserEntity(userEntity)).thenReturn(Optional.of(fav));
        Mockito.doNothing().when(favoriteRepository).delete(fav);
        Mockito.doNothing().when(userRepository).delete(userEntity);

        userDeleteServiceImpl.deleteUser(userDeleteRequest);

        Assert.assertEquals(passwordResult, encryptedPassword);

        Mockito.verify(passwordEncryptorUtil).encryptPassword(rawPassword);
        Mockito.verify(userRepository).findByEmail(userDeleteRequest.getEmail());
        Mockito.verify(favoriteRepository).delete(fav);
        Mockito.verify(userRepository).delete(userEntity);
        Mockito.verify(passwordEncryptorUtil).isMatch(userDeleteRequest.getPassword(), userEntity.getPassword());

    }
}