package realtyhub.advert.repository;

import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.photos.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {


    void deleteByAdvertEntity(
            final AdvertEntity advertEntity
    );
}
