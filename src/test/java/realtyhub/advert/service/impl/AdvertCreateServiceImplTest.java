package realtyhub.advert.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import realtyhub.advert.model.dto.request.advert.AdvertCreateRequest;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.AdvertNumberGenerateService;

@ExtendWith(MockitoExtension.class)
public class AdvertCreateServiceImplTest {
    @InjectMocks
    private AdvertCreateServiceImpl advertCreateService;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private AdvertNumberGenerateService advertNumberGenerateService;

    private AdvertCreateRequest advertCreateRequest;

    @BeforeEach
    void setUp() {
    }
}
