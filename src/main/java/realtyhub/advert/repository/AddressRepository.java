package realtyhub.advert.repository;

import org.springframework.stereotype.Repository;
import realtyhub.advert.model.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
