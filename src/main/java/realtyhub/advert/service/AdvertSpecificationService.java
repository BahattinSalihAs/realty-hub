package realtyhub.advert.service;

import org.springframework.data.jpa.domain.Specification;
import realtyhub.advert.model.dto.request.advert.AdvertFilterRequest;
import realtyhub.advert.model.entity.AdvertEntity;

public interface AdvertSpecificationService {
    Specification<AdvertEntity> filterBy(AdvertFilterRequest filter);
}
