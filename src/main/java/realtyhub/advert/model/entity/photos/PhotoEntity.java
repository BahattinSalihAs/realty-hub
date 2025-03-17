package realtyhub.advert.model.entity.photos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import realtyhub.advert.model.entity.AdvertEntity;


/**
 * @author BSA
 * Date 25.01.25
 */
@Entity
@Table(name = "photo_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "advert_entity")
    @JsonBackReference
    private AdvertEntity advertEntity;

    @Column(name = "file_path")
    private String filePath;


}
