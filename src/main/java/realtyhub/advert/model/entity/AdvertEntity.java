package realtyhub.advert.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import realtyhub.advert.model.entity.enums.*;
import realtyhub.advert.model.entity.photos.PhotoEntity;
import realtyhub.common.serialize.BigDecimalSerializer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author BSA
 * Date 25.01.25
 */
@Entity
@Table(name = "adverts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public final class AdvertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title")
    @Size(max = 50)
    private String title;

    @NotNull
    @JsonSerialize(using = BigDecimalSerializer.class)
    @Column(name = "price",nullable = false)
    @Positive
    private BigDecimal advertPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code", nullable = false)
    private CurrencyCode advertCurrencyCode;

    @OneToOne(mappedBy = "advertEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private AddressEntity addressEntity;

    @Column(name = "advert_id", unique = true, nullable = false)
    private Long advertId;

    @Column(name = "advert_date")
    private LocalDate advertDate;

    @NotNull
    @Column(name = "gross_area")
    private int grossArea;

    @NotNull
    @Column(name = "net_area")
    private int netArea;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @NotNull
    @Column(name = "build_age")
    @PositiveOrZero(message = "Build age must be positive or zero!")
    private int buildAge;

    @NotNull
    @Column(name = "floor_number")
    private int floorNumber;

    @NotNull
    @Column(name = "total_floors")
    @Positive(message = "Total floor just must be positive!")
    private int totalFloors;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "heat_type", nullable = false)
    private HeatType heatType; //enum

    @NotNull
    @Column(name = "bath_number")
    @Positive(message = "Bath number just must be positive!")
    private int bathNumber;

    @NotNull
    @Column(name = "is_balcony")
    private boolean isBalcony;

    @NotNull
    @Column(name = "is_with_furniture")
    private boolean isWithFurniture;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "use_case", nullable = false)
    private UseCase useCase; //enum

    @NotNull
    @Column(name = "is_side_inside")
    private boolean isSideInSide;

    @NotBlank
    @Column(name = "advert_description")
    @Size(max =250)
    private String advertDescription;

    @ElementCollection(targetClass = FeatureType.class)
    @Enumerated(EnumType.STRING)
    private List<FeatureType> features;

    @OneToMany(mappedBy = "advertEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PhotoEntity> photos = new ArrayList<>();

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;


}
