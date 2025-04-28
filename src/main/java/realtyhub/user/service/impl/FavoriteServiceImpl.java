package realtyhub.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.common.error.UserNotFoundException;
import realtyhub.user.model.dto.request.FavoriteGetRequest;
import realtyhub.user.model.dto.request.FavoriteRequest;
import realtyhub.user.model.entity.FavoriteEntity;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.repository.FavoriteRepository;
import realtyhub.user.repository.UserRepository;
import realtyhub.user.service.FavoriteService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;

    @Override
    public void addFavorite(
            final FavoriteRequest favoriteRequest
            ) {
        UserEntity userEntityFromDB = userRepository.findByEmail(favoriteRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!userEntityFromDB.getUserRole().equals(UserRole.CUSTOMER)) {
            throw new RuntimeException("User is not a customer");
        }

        final FavoriteEntity favoriteEntity = favoriteRepository.findByUserEntity(userEntityFromDB)
                .orElseGet(() -> {
                    FavoriteEntity newFavoriteEntity = FavoriteEntity.builder()
                            .userEntity(userEntityFromDB)
                            .adverts(new ArrayList<>())
                            .favoritedAt(LocalDateTime.now())
                            .build();
                    return favoriteRepository.save(newFavoriteEntity);
                });
        for (Long advertId: favoriteRequest.getAdvertIdList()){
            AdvertEntity advert = advertRepository.findByAdvertId(advertId)
                    .orElseThrow(() -> new RuntimeException("Advert not found"));
            if (!favoriteEntity.getAdverts().contains(advert)) {
                favoriteEntity.getAdverts().add(advert);
            }
        }
        favoriteRepository.save(favoriteEntity);

    }

    @Override
    public void removeFavorite(
            final FavoriteRequest favoriteRequest
    ) {
        UserEntity userEntityFromDB = userRepository.findByEmail(favoriteRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!userEntityFromDB.getUserRole().equals(UserRole.CUSTOMER)) {
            throw new RuntimeException("User is not a customer");
        }

        FavoriteEntity favoriteEntityFromDB = favoriteRepository.findByUserEntity(userEntityFromDB)
                .orElseThrow(() -> new RuntimeException("Favorite list not found"));

        favoriteEntityFromDB.getAdverts().removeIf(advertEntity -> favoriteRequest.getAdvertIdList().contains(advertEntity.getId()));

        favoriteRepository.delete(favoriteEntityFromDB);
    }

    @Override
    public List<AdvertEntity> getFavorites(
            final FavoriteGetRequest favoriteGetRequest
    ) {

        final UserEntity userEntityFromDB = userRepository.findByEmail(favoriteGetRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!userEntityFromDB.getUserRole().equals(UserRole.CUSTOMER)) {
            throw new RuntimeException("User is not a customer");
        }

        FavoriteEntity favoriteEntityFromDB = favoriteRepository.findByUserEntity(userEntityFromDB)
                .orElseThrow(() -> new RuntimeException("Favorite list not found"));


        return favoriteEntityFromDB.getAdverts();
    }
}
