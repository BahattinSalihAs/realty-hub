package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.AdvertNumberGenerateService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public final class AdvertNumberGenerateServiceImpl implements AdvertNumberGenerateService {
    private final AdvertRepository advertRepository;
    private final Random random = new Random();

    @Override
    public final long generateAdvertNumber() {
        Long advertNumber;
        do {
            advertNumber = generateRandomAdvertNumber();
        }while (advertRepository.existsByAdvertId(advertNumber));

        return advertNumber;
    }

    private final Long generateRandomAdvertNumber() {
        return 100000L + random.nextInt(900000);
    }
}
