package realtyhub.advert.service;

import realtyhub.advert.model.dto.request.advert.AdvertActivationRequest;

public interface AdvertActivationService {
    void activateAdvert(
            final AdvertActivationRequest advertActivationRequest
    );
}
