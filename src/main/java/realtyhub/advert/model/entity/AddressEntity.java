package realtyhub.advert.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "advert_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private AdvertEntity advertEntity;

    private String city;
    private String district;
    private String neighborhood;
}
