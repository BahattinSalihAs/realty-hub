package realtyhub.advert.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.advert.model.dto.request.advert.AdvertDeleteRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.photos.PhotoEntity;
import realtyhub.advert.repository.AddressRepository;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.repository.PhotoRepository;
import realtyhub.advert.service.AdvertNumberGenerateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdvertDeleteServiceImplTest {

    @Mock
    private AdvertRepository advertRepository;

    @InjectMocks
    private AdvertDeleteServiceImpl advertDeleteServiceImpl;

    @Mock
    private PhotoRepository photoRepository;

    @Test
    void testDeleteAdvertWithValidAdvertId() {
        final AdvertNumberGenerateServiceImpl numbers = new AdvertNumberGenerateServiceImpl(advertRepository);
        PhotoEntity pE = new PhotoEntity();
        final List<PhotoEntity> photos = new ArrayList<>();
        photos.add(pE);

        final AdvertDeleteRequest advertDeleteRequest = AdvertDeleteRequest.builder()
                .advertId(numbers.generateAdvertNumber())
                .build();

        final AdvertEntity advert = AdvertEntity.builder()
                .advertId(advertDeleteRequest.getAdvertId())
                .photos(photos)
                .buildAge(30)
                .build();

        // whens
        Mockito.when(advertRepository.findByAdvertId(advert.getAdvertId())).thenReturn(Optional.of(advert));
        Mockito.doNothing().when(photoRepository).deleteByAdvertEntity(advert);
        Mockito.doNothing().when(advertRepository).delete(advert);

        advertDeleteServiceImpl.deleteAdvert(advertDeleteRequest);

        // verify
        Mockito.verify(advertRepository).delete(advert);
        Mockito.verify(photoRepository).deleteByAdvertEntity(advert);
        Mockito.verify(advertRepository).findByAdvertId(advert.getAdvertId());
    }
}