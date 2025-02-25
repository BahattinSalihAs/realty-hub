package realtyhub.advert.service;

import realtyhub.advert.model.dto.request.advert.AdvertUpdateRequest;

public interface AdvertUpdateService {

    void updateAdvert(
            final AdvertUpdateRequest request
            );
}
