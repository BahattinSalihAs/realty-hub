package realtyhub.user.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.impl.AdvertNumberGenerateServiceImpl;
import realtyhub.user.model.dto.request.FavoriteGetRequest;
import realtyhub.user.model.dto.request.FavoriteRequest;
import realtyhub.user.model.entity.FavoriteEntity;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.repository.FavoriteRepository;
import realtyhub.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {

    @InjectMocks
    private FavoriteServiceImpl favoriteServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Test
    final void shouldAddFavoriteSuccessfullyWithCurrentCustomer() {
        final List<Long> advertIdList = new ArrayList<>();
        final List<AdvertEntity> adverts = new ArrayList<>();
        final String email = "test@test.com";
        final AdvertNumberGenerateServiceImpl numbers = new AdvertNumberGenerateServiceImpl(advertRepository);

        final AdvertEntity advertEntity = AdvertEntity.builder()
                .advertId(numbers.generateAdvertNumber())
                .isActive(true)
                .build();
        advertIdList.add(advertEntity.getAdvertId());
        adverts.add(advertEntity);

        final FavoriteRequest favoriteRequest = FavoriteRequest.builder()
                .advertIdList(advertIdList)
                .email(email)
                .build();

        final UserEntity customer = UserEntity.builder()
                .email(favoriteRequest.getEmail())
                .userRole(UserRole.CUSTOMER)
                .age(30)
                .build();

        Mockito.when(userRepository.findByEmail(favoriteRequest.getEmail())).thenReturn(Optional.of(customer));

        final FavoriteEntity favoriteEntity = Mockito.mock(FavoriteEntity.class);
        favoriteEntity.setAdverts(adverts);
        favoriteEntity.setFavoritedAt(LocalDateTime.now());
        favoriteEntity.setUserEntity(customer);

        Mockito.when(favoriteRepository.findByUserEntity(customer)).thenReturn(Optional.of(favoriteEntity));
        Mockito.when(advertRepository.findByAdvertId(advertEntity.getAdvertId())).thenReturn(Optional.of(advertEntity));
        Mockito.when(favoriteRepository.save(favoriteEntity)).thenReturn(favoriteEntity);
        Mockito.when(favoriteEntity.getAdverts()).thenReturn(adverts);

        favoriteServiceImpl.addFavorite(favoriteRequest);

        Assert.assertEquals(customer.getUserRole(), UserRole.CUSTOMER);

        Mockito.verify(userRepository).findByEmail(favoriteRequest.getEmail());
        Mockito.verify(advertRepository).findByAdvertId(advertEntity.getAdvertId());
        Mockito.verify(favoriteRepository).save(favoriteEntity);
        Mockito.verify(favoriteRepository).findByUserEntity(customer);
        Mockito.verify(favoriteEntity, Mockito.times(2)).getAdverts();

    }

    @Test
    final void shouldRemoveFavoriteSuccessfullyWithCurrentCustomer() {
        final List<Long> advertIdList = new ArrayList<>();
        final List<AdvertEntity> adverts = new ArrayList<>();
        final String email = "test@test.com";
        final AdvertNumberGenerateServiceImpl numbers = new AdvertNumberGenerateServiceImpl(advertRepository);

        final AdvertEntity advertEntity = AdvertEntity.builder()
                .advertId(numbers.generateAdvertNumber())
                .isActive(true)
                .build();
        advertIdList.add(advertEntity.getAdvertId());
        adverts.add(advertEntity);

        final FavoriteRequest fav = FavoriteRequest.builder()
                .email(email)
                .advertIdList(advertIdList)
                .build();

        final UserEntity customer = UserEntity.builder()
                .email(fav.getEmail())
                .userRole(UserRole.CUSTOMER)
                .build();

        Mockito.when(userRepository.findByEmail(fav.getEmail())).thenReturn(Optional.of(customer));
        final FavoriteEntity favoriteEntity = Mockito.mock(FavoriteEntity.class);
        favoriteEntity.setAdverts(adverts);
        favoriteEntity.setFavoritedAt(LocalDateTime.now());
        favoriteEntity.setUserEntity(customer);

        Mockito.when(favoriteRepository.findByUserEntity(customer)).thenReturn(Optional.of(favoriteEntity));
        Mockito.doNothing().when(favoriteRepository).delete(favoriteEntity);

        favoriteServiceImpl.removeFavorite(fav);

        Assert.assertEquals(customer.getUserRole(), UserRole.CUSTOMER);

        Mockito.verify(userRepository).findByEmail(fav.getEmail());
        Mockito.verify(favoriteRepository).delete(favoriteEntity);
        Mockito.verify(favoriteRepository).findByUserEntity(customer);


    }

    @Test
    final void shouldReturnFavoritesSuccessfullyWithCurrentCustomer() {
        final List<AdvertEntity> adverts = new ArrayList<>();
        final String email = "test@test.com";
        final AdvertNumberGenerateServiceImpl numbers = new AdvertNumberGenerateServiceImpl(advertRepository);

        final AdvertEntity advertEntity = AdvertEntity.builder()
                .advertId(numbers.generateAdvertNumber())
                .isActive(true)
                .build();
        adverts.add(advertEntity);

        final FavoriteGetRequest fav = FavoriteGetRequest.builder()
                .email(email)
                .build();

        final UserEntity customer = UserEntity.builder()
                .email(fav.getEmail())
                .userRole(UserRole.CUSTOMER)
                .build();

        Mockito.when(userRepository.findByEmail(fav.getEmail())).thenReturn(Optional.of(customer));
        final FavoriteEntity favoriteEntity = FavoriteEntity.builder()
                .adverts(adverts)
                .favoritedAt(LocalDateTime.now())
                .userEntity(customer)
                .build();

        Mockito.when(favoriteRepository.findByUserEntity(customer)).thenReturn(Optional.of(favoriteEntity));

        final List<AdvertEntity> result = favoriteServiceImpl.getFavorites(fav);

        Assert.assertEquals(favoriteEntity.getAdverts().size(), result.size());
        Assert.assertEquals(favoriteEntity.getAdverts().get(0).getAdvertId(), result.get(0).getAdvertId());
        Assert.assertEquals(favoriteEntity.getAdverts(), result);
        Assert.assertNotNull(result);
        Assert.assertEquals(customer.getUserRole(), UserRole.CUSTOMER);

        Mockito.verify(userRepository).findByEmail(fav.getEmail());
        Mockito.verify(favoriteRepository).findByUserEntity(customer);

    }
}