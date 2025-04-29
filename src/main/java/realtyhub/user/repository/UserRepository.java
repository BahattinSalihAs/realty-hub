package realtyhub.user.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
@EnableJpaRepositories(basePackages = "src.main.java.realtyhub.user.repository")
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

    Optional<UserEntity> findUserEntityByUserRole(
            final UserRole userRole
    );

    Optional<UserEntity> findByEmailAndUserRole(
            final String email,
            final UserRole userRole
    );
}
