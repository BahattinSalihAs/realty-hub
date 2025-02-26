package realtyhub.advert.service;

import realtyhub.advert.model.dto.request.advert.AdvertFilterRequest;
import realtyhub.advert.model.entity.AdvertEntity;

import java.util.List;

public interface AdvertFilterService {
    List<AdvertEntity> searchAdverts(
            final AdvertFilterRequest filter
    );
}
