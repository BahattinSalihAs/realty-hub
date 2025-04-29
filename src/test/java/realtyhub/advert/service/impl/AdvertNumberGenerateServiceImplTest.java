package realtyhub.advert.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import realtyhub.advert.repository.AdvertRepository;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
class AdvertNumberGenerateServiceImplTest {

    @InjectMocks
    private AdvertNumberGenerateServiceImpl advertNumberGenerateServiceImpl;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private Random random = new Random();

    @Test
    void shouldReturnUniqueAdvertNumber() {
        final Long oldAdvertNumber = 12345L;
        final Long newAdvertNumber = 6789L;

        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(oldAdvertNumber.intValue()).thenReturn(newAdvertNumber.intValue());
        Mockito.when(advertRepository.existsByAdvertId(oldAdvertNumber)).thenReturn(true);
        Mockito.when(advertRepository.existsByAdvertId(newAdvertNumber)).thenReturn(false);
        //Mockito.when(advertNumberGenerateServiceImpl.generateAdvertNumber()).thenReturn(newAdvertNumber);
        final Long advertId = advertNumberGenerateServiceImpl.generateAdvertNumber();

        Assert.assertNotEquals(oldAdvertNumber, advertId);

        Mockito.verify(advertRepository, Mockito.times(2)).existsByAdvertId(Mockito.anyLong());

    }
}