package realtyhub.user.repository;

import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(
            final String email
    );

    boolean existsUserEntityByEmail(
            final String email
    );

    boolean existsUserEntityByPhoneNumber(
            final String phoneNumber
    );

    long countUserEntityByUserRole(
            final UserRole userRole
    );

    UserEntity findUserEntityByUserRole(
            final UserRole userRole
    );
}
