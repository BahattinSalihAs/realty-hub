package realtyhub.advert.service;

import realtyhub.advert.model.dto.request.advert.AdvertDeleteRequest;

public interface AdvertDeleteService {

    void deleteAdvert(
            final AdvertDeleteRequest advertDeleteRequest
    );
}
