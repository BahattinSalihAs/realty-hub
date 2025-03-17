package realtyhub.advert.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.advert.model.dto.request.advert.AdvertDeleteRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AddressRepository;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.repository.PhotoRepository;
import realtyhub.advert.service.AdvertNumberGenerateService;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdvertDeleteServiceImplTest {

    @Mock
    private AdvertRepository advertRepository;

    @InjectMocks
    private AdvertDeleteServiceImpl advertDeleteServiceImpl;

    @Test
    void testDeleteAdvertWithValidAdvertId() {
        final AdvertNumberGenerateServiceImpl numbers = new AdvertNumberGenerateServiceImpl(advertRepository);

        final AdvertDeleteRequest advertDeleteRequest = AdvertDeleteRequest.builder()
                .advertId(numbers.generateAdvertNumber())
                .build();

        final AdvertEntity advert = AdvertEntity.builder()
                .advertId(advertDeleteRequest.getAdvertId())
                .photos(new ArrayList<>())
                .buildAge(30)
                .build();

        // whens
        Mockito.when(advertRepository.findByAdvertId(advert.getAdvertId())).thenReturn(Optional.of(advert));
        Mockito.doNothing().when(advertRepository).delete(advert);

        advertDeleteServiceImpl.deleteAdvert(advertDeleteRequest);

        // verify
        Mockito.verify(advertRepository).delete(advert);
        Mockito.verify(advertRepository).findByAdvertId(advert.getAdvertId());
    }
}