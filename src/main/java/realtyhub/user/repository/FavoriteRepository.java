package realtyhub.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.user.model.entity.FavoriteEntity;
import realtyhub.user.model.entity.UserEntity;
import java.util.Optional;

@Repository
public interface FavoriteRepository  extends JpaRepository<FavoriteEntity, Long> {

    Optional<FavoriteEntity> findByUserEntity(
            final UserEntity userEntity
    );
}
