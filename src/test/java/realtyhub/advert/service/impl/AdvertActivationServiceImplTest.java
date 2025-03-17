package realtyhub.advert.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.advert.model.dto.request.advert.AdvertActivationRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.AdvertNumberGenerateService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdvertActivationServiceImplTest {

    @InjectMocks
    private AdvertActivationServiceImpl advertActivationServiceImpl;

    @Mock
    private AdvertRepository advertRepository;

    @Test
    void shouldActivateAdvertWhenValidData() {
        final AdvertNumberGenerateService numbers = new AdvertNumberGenerateService() {
            @Override
            public long generateAdvertNumber() {
                return 12345L;
            }
        };
        final AdvertActivationRequest advertActivationRequest = AdvertActivationRequest.builder()
                .advertId(numbers.generateAdvertNumber())
                .build();

        final AdvertEntity advertEntity = AdvertEntity.builder()
                .advertId(advertActivationRequest.getAdvertId())
                .isActive(true)
                .build();
        Mockito.when(advertRepository.findByAdvertId(advertActivationRequest.getAdvertId())).thenReturn(Optional.of(advertEntity));
        Mockito.when(advertRepository.save(Mockito.any(AdvertEntity.class))).thenReturn(advertEntity);

        advertActivationServiceImpl.activateAdvert(advertActivationRequest);

        Mockito.verify(advertRepository).save(Mockito.any(AdvertEntity.class));
        Mockito.verify(advertRepository).findByAdvertId(advertActivationRequest.getAdvertId());
    }
}