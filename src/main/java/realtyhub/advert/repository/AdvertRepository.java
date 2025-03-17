package realtyhub.advert.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import realtyhub.advert.model.entity.AdvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<AdvertEntity, Long>, JpaSpecificationExecutor<AdvertEntity> {

    Optional<AdvertEntity> findByAdvertId(
            final Long advertId
    );

    boolean existsByAdvertId(
            final Long advertId
    );

}
