package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import realtyhub.advert.model.dto.request.advert.AdvertDeactivationRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.AdvertDeactivationService;

@Service
@RequiredArgsConstructor
final class AdvertDeactivationServiceImpl implements AdvertDeactivationService {
    private final AdvertRepository advertRepository;

    @Override
    public final void deactivateAdvert(
            final AdvertDeactivationRequest advertDeactivationRequest
    ) {
        final AdvertEntity advertEntityFromDB = advertRepository.findByAdvertId(advertDeactivationRequest.getAdvertId())
                .orElseThrow(() -> new RuntimeException("Advert not found"));
        advertEntityFromDB.setActive(false);
        advertRepository.save(advertEntityFromDB);
    }
}
