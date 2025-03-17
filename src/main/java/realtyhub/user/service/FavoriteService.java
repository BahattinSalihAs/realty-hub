package realtyhub.user.service;

import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.user.model.dto.request.FavoriteGetRequest;
import realtyhub.user.model.dto.request.FavoriteRequest;

import java.util.List;

public interface FavoriteService {
    void addFavorite(
            final FavoriteRequest favoriteRequest
            );

    void removeFavorite(
            final FavoriteRequest favoriteRequest
    );

    List<AdvertEntity> getFavorites(
            final FavoriteGetRequest favoriteGetRequest
    );
}
