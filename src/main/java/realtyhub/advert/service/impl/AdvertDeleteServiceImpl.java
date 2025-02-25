package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.advert.model.dto.request.advert.AdvertDeleteRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AddressRepository;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.repository.PhotoRepository;
import realtyhub.advert.service.AdvertDeleteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
final class AdvertDeleteServiceImpl implements AdvertDeleteService {
    private final AdvertRepository advertRepository;
    private final AddressRepository addressRepository;
    private final PhotoRepository photoRepository;

    @Override
    public final void deleteAdvert(
            final AdvertDeleteRequest advertDeleteRequest
    ) {
        AdvertEntity advertEntityFromDB = advertRepository.findByAdvertId(advertDeleteRequest.getAdvertId())
                .orElseThrow(() -> new RuntimeException("Advert not found"));

        //advertEntityFromDB = advertRepository.save(advertEntityFromDB);

        advertRepository.delete(advertEntityFromDB);
    }
}
