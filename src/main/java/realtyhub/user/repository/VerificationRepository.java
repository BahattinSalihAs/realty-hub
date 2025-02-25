package realtyhub.user.repository;


import jakarta.transaction.Transactional;
import realtyhub.user.model.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerificationRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmail(
            final String email
    );

    boolean existsByEmail(
            final String email
    );

    @Transactional
    @Modifying
    @Query("DELETE FROM VerificationCode v WHERE v.expiryDate < :now")
    void deleteByExpiryDateBefore(
            final LocalDateTime now
    );


}
