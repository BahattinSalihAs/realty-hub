package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import realtyhub.advert.model.dto.request.advert.AdvertActivationRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.AdvertActivationService;

@Service
@RequiredArgsConstructor
public class AdvertActivationServiceImpl implements AdvertActivationService {
    private final AdvertRepository advertRepository;

    @Override
    public final void activateAdvert(
            final AdvertActivationRequest advertActivationRequest
    ) {
        AdvertEntity advertEntity = advertRepository.findByAdvertId(advertActivationRequest.getAdvertId())
                .orElseThrow(() -> new RuntimeException("Advert not found"));
        advertEntity.setActive(true);
        advertRepository.save(advertEntity);

    }
}
