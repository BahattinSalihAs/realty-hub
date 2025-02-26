package realtyhub.advert.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import realtyhub.advert.model.dto.request.advert.AdvertFilterRequest;
import realtyhub.advert.model.entity.AddressEntity;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.service.AdvertSpecificationService;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertSpecificationServiceImpl implements AdvertSpecificationService {

    @Override
    public Specification<AdvertEntity> filterBy(
            final AdvertFilterRequest filter
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<AdvertEntity, AddressEntity> addressJoin = root.join("addressEntity", JoinType.INNER);

            if (filter.getCity() != null) {
                predicates.add(criteriaBuilder.equal(addressJoin.get("city"), filter.getCity()));
            }
            if (filter.getDistrict() != null) {
                predicates.add(criteriaBuilder.equal(addressJoin.get("district"), filter.getDistrict()));
            }
            if (filter.getNeighborhood() != null) {
                predicates.add(criteriaBuilder.equal(addressJoin.get("neighborhood"), filter.getNeighborhood()));
            }
            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("advertPrice"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("advertPrice"), filter.getMaxPrice()));
            }
            if (filter.getMinArea() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("grossArea"), filter.getMinArea()));
            }
            if (filter.getMaxArea() != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("grossArea"), filter.getMaxArea()));
            }
            if (filter.getRoomTypes() != null && !filter.getRoomTypes().isEmpty()) {
                predicates.add(root.get("roomType").in(filter.getRoomTypes()));
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
