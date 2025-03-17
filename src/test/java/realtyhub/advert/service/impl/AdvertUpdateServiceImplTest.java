package realtyhub.advert.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import realtyhub.advert.model.dto.request.advert.AdvertUpdateRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.enums.CurrencyCode;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.repository.PhotoRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdvertUpdateServiceImplTest {

    @InjectMocks
    private AdvertUpdateServiceImpl advertUpdateServiceImpl;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Test
    void shouldUpdateAdvertWithValidData() {
        final AdvertNumberGenerateServiceImpl numbers = new AdvertNumberGenerateServiceImpl(advertRepository);

        final AdvertUpdateRequest advertUpdateRequest = AdvertUpdateRequest.builder()
                .advertId(numbers.generateAdvertNumber())
                .advertPrice(BigDecimal.TEN)
                .advertCurrencyCode(CurrencyCode.TRY)
                .buildAge(30)
                .build();

        final AdvertEntity advert = AdvertEntity.builder()
                .advertId(advertUpdateRequest.getAdvertId())
                .advertPrice(advertUpdateRequest.getAdvertPrice())
                .advertCurrencyCode(advertUpdateRequest.getAdvertCurrencyCode())
                .buildAge(advertUpdateRequest.getBuildAge())
                .build();

        Mockito.when(advertRepository.findByAdvertId(advertUpdateRequest.getAdvertId())).thenReturn(Optional.of(advert));
        Mockito.when(advertRepository.save(advert)).thenReturn(advert);

        advertUpdateServiceImpl.updateAdvert(advertUpdateRequest);

        Mockito.verify(advertRepository).findByAdvertId(advertUpdateRequest.getAdvertId());
        Mockito.verify(advertRepository).save(advert);
    }
}