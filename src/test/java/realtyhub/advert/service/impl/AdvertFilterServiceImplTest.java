package realtyhub.advert.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import realtyhub.advert.model.dto.request.advert.AdvertFilterRequest;
import realtyhub.advert.model.entity.AddressEntity;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.enums.AdvertDateRange;
import realtyhub.advert.model.entity.enums.AdvertSortType;
import realtyhub.advert.model.entity.enums.RoomType;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.AdvertSpecificationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdvertFilterServiceImplTest {

    @InjectMocks
    private AdvertFilterServiceImpl advertFilterServiceImpl;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private AdvertSpecificationService advertSpecificationService;

    @Test
    void shouldReturnFilteredAdvertsWithValidFilter() {
        final AddressEntity addressEntity = Mockito.mock(AddressEntity.class);
        addressEntity.setCity("Denizli");
        addressEntity.setDistrict("Merkezefendi");
        addressEntity.setNeighborhood("Karaman");

        final AddressEntity addressEntity2 = Mockito.mock(AddressEntity.class);
        addressEntity.setCity("Denizli");
        addressEntity.setDistrict("Merkezefendi");
        addressEntity.setNeighborhood("Akkonak");

        final AdvertEntity advertEntity = Mockito.mock(AdvertEntity.class);
        advertEntity.setAdvertDate(LocalDate.now().minusDays(1));
        advertEntity.setAddressEntity(addressEntity);
        advertEntity.setGrossArea(100);
        advertEntity.setAdvertPrice(BigDecimal.valueOf(1000000));
        advertEntity.setRoomType(RoomType.ONE_PLUS_ONE);

        final AdvertEntity advertEntity2 = Mockito.mock(AdvertEntity.class);
        advertEntity2.setAdvertDate(LocalDate.now().minusDays(10));
        advertEntity2.setAddressEntity(addressEntity2);
        advertEntity2.setGrossArea(250);
        advertEntity2.setAdvertPrice(BigDecimal.valueOf(100));
        advertEntity2.setRoomType(RoomType.FIVE_PLUS_ONE);

        final AdvertFilterRequest advertFilterRequest = AdvertFilterRequest.builder()
                .advertDateRange(AdvertDateRange.LAST_3_DAYS)
                .advertSortType(AdvertSortType.DATE_OLDEST)
                .city("Denizli")
                .district("Merkezefendi")
                .neighborhood("Karaman")
                .maxArea(120)
                .minArea(50)
                .maxPrice(BigDecimal.valueOf(5000000))
                .minPrice(BigDecimal.valueOf(500000))
                .roomType(RoomType.ONE_PLUS_ONE)
                .build();

        final AdvertSpecificationService service = Mockito.mock(AdvertSpecificationService.class);
        final List<AdvertEntity> adverts2 = new ArrayList<>();
        adverts2.add(advertEntity);

        Mockito.when(advertRepository.findAll(service.filterBy(advertFilterRequest)))
                .thenReturn(adverts2);

        final List<AdvertEntity> adverts = advertFilterServiceImpl.searchAdverts(advertFilterRequest);

        Assert.assertEquals(advertEntity, adverts.get(0));

        Mockito.verify(advertRepository).findAll(service.filterBy(advertFilterRequest));

    }
}