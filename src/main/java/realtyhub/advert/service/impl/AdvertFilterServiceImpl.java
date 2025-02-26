package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import realtyhub.advert.model.dto.request.advert.AdvertFilterRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.AdvertFilterService;
import realtyhub.advert.service.AdvertSpecificationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertFilterServiceImpl implements AdvertFilterService {
    private final AdvertRepository advertRepository;
    private final AdvertSpecificationService advertSpecificationService;

    @Override
    public List<AdvertEntity> searchAdverts(AdvertFilterRequest filter) {
        return advertRepository.findAll(advertSpecificationService.filterBy(filter));
    }
}
