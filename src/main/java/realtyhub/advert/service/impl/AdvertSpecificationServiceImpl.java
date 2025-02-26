package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import realtyhub.advert.model.dto.request.advert.AdvertFilterRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.service.AdvertSpecificationService;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertSpecificationServiceImpl implements AdvertSpecificationService {

    @Override
    public Specification<AdvertEntity> filterBy(
            final AdvertFilterRequest filter
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("city"), filter.getCity()));
            }
            if (filter.getDistrict() != null) {
                predicates.add(criteriaBuilder.equal(root.get("district"), filter.getDistrict()));
            }
            if (filter.getNeighborhood() != null) {
                predicates.add(criteriaBuilder.equal(root.get("neighborhood"), filter.getNeighborhood()));
            }
            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("advertPrice"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("advertPrice"), filter.getMaxPrice()));
            }
            if (filter.getMinArea() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("area"), filter.getMinArea()));
            }
            if (filter.getMaxArea() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("area"), filter.getMaxArea()));
            }
            if (filter.getRoomType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("roomType"), filter.getRoomType()));
            }
            if (filter.getAdvertDateRange() != null) {
                LocalDateTime startDate = LocalDateTime.now().minusDays(filter.getAdvertDateRange().getDays());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("advertDate"), startDate));
            }

            if (filter.getAdvertSortType() != null) {
                if (filter.getAdvertSortType().getDirection() == Sort.Direction.ASC) {
                    query.orderBy(criteriaBuilder.asc(root.get(filter.getAdvertSortType().getField())));
                }else {
                    query.orderBy(criteriaBuilder.desc(root.get(filter.getAdvertSortType().getField())));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
