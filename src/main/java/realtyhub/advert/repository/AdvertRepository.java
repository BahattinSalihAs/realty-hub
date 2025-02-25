package realtyhub.advert.repository;

import realtyhub.advert.model.entity.AdvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertRepository extends JpaRepository<AdvertEntity, Long> {

    Optional<AdvertEntity> findByAdvertId(
            final Long advertId
    );

    boolean existsByAdvertId(
            final Long advertId
    );
}
