package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import realtyhub.advert.model.dto.request.advert.AdvertDeleteRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.photos.PhotoEntity;
import realtyhub.advert.repository.AddressRepository;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.repository.PhotoRepository;
import realtyhub.advert.service.AdvertDeleteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AdvertDeleteServiceImpl implements AdvertDeleteService {
    private final AdvertRepository advertRepository;
    private final PhotoRepository photoRepository;

    @Transactional
    @Override
    public void deleteAdvert(
            final AdvertDeleteRequest advertDeleteRequest
    ) {
        final AdvertEntity advertEntityFromDB = advertRepository.findByAdvertId(advertDeleteRequest.getAdvertId())
                .orElseThrow(() -> new RuntimeException("Advert not found"));
        photoRepository.deleteByAdvertEntity(advertEntityFromDB);
        advertRepository.delete(advertEntityFromDB);
    }
}
