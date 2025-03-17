package realtyhub.advert.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "address_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class AddressEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String district;
    private String neighborhood;
}
