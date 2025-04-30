package realtyhub.advert.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.advert.model.dto.request.advert.AdvertDeactivationRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.AdvertNumberGenerateService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdvertDeactivationServiceImplTest {

    @InjectMocks
    private AdvertDeactivationServiceImpl advertDeactivationServiceImpl;

    @Mock
    private AdvertRepository advertRepository;

    @Test
    void shouldDeactivateAdvertWhenValidData() {
        final AdvertNumberGenerateService numbers = new AdvertNumberGenerateService() {
            @Override
            public long generateAdvertNumber() {
                return 65498L;
            }
        };
        final AdvertDeactivationRequest advertDeactivationRequest = AdvertDeactivationRequest.builder()
                .advertId(numbers.generateAdvertNumber())
                .build();

        final AdvertEntity advertEntity = AdvertEntity.builder()
                .advertId(advertDeactivationRequest.getAdvertId())
                .isActive(false)
                .build();

        Mockito.when(advertRepository.findByAdvertId(advertDeactivationRequest.getAdvertId())).thenReturn(Optional.of(advertEntity));
        Mockito.when(advertRepository.save(Mockito.any(AdvertEntity.class))).thenReturn(advertEntity);

        advertDeactivationServiceImpl.deactivateAdvert(advertDeactivationRequest);

        Mockito.verify(advertRepository).save(Mockito.any(AdvertEntity.class));
        Mockito.verify(advertRepository).findByAdvertId(advertDeactivationRequest.getAdvertId());

    }
}