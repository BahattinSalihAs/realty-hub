package realtyhub.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import realtyhub.advert.model.entity.AdvertEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FavoriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity userEntity;

    @ManyToMany
    @JoinTable(
            name = "favorite_adverts",
            joinColumns = @JoinColumn(name = "favorite_id"),
            inverseJoinColumns = @JoinColumn(name = "advert_id")
    )
    @JsonIgnore
    private List<AdvertEntity> adverts = new ArrayList<>();

    private LocalDateTime favoritedAt;
}
