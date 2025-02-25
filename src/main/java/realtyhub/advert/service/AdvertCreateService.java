package realtyhub.advert.service;

import realtyhub.advert.model.dto.request.advert.AdvertCreateRequest;

public interface AdvertCreateService {

    void createAdvert(
            final AdvertCreateRequest advertCreateRequest
    );
}
