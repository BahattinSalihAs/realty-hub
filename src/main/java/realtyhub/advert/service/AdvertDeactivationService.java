package realtyhub.advert.service;

import realtyhub.advert.model.dto.request.advert.AdvertDeactivationRequest;

public interface AdvertDeactivationService {
    void deactivateAdvert(
            final AdvertDeactivationRequest advertDeactivationRequest
    );
}
