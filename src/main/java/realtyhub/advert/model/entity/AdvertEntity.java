package realtyhub.advert.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import realtyhub.advert.model.dto.request.advert.AdvertCreateRequest;
import realtyhub.advert.model.entity.enums.*;
import realtyhub.advert.model.entity.photos.PhotoEntity;
import realtyhub.common.serialize.BigDecimalSerializer;
import realtyhub.user.model.entity.UserEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
@ToString
public final class AdvertEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
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
    private int totalBathNumber;

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

    @OneToMany(mappedBy = "advertEntity")
    @JsonManagedReference
    private List<PhotoEntity> photos = new ArrayList<>();

    @OneToOne
    private AddressEntity addressEntity;

    @NotNull
    @Column(name = "is_active")
    @JsonIgnore
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "realtor_id")
    private UserEntity realtor;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AdvertCreateRequest advert)) return false;
        return getGrossArea() == advert.getGrossArea() && getNetArea() == advert.getNetArea() && getBuildAge() == advert.getBuildAge() && getFloorNumber() == advert.getFloorNumber() && getTotalFloors() == advert.getTotalFloors() && getTotalBathNumber() == advert.getTotalBathNumber() && isBalcony() == advert.isBalcony() && isWithFurniture() == advert.isWithFurniture() && isSideInSide() == advert.isSideInSide() && Objects.equals(getTitle(), advert.getTitle()) && Objects.equals(getAdvertPrice(), advert.getAdvertPrice()) && getAdvertCurrencyCode() == advert.getAdvertCurrencyCode() && getRoomType() == advert.getRoomType() && getHeatType() == advert.getHeatType() && getUseCase() == advert.getUseCase() && Objects.equals(getAdvertDescription(), advert.getAdvertDescription()) && Objects.equals(getFeatures(), advert.getFeatures());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getAdvertPrice(), getAdvertCurrencyCode(), getGrossArea(), getNetArea(), getRoomType(), getBuildAge(), getFloorNumber(), getTotalFloors(), getHeatType(), getTotalBathNumber(), isBalcony(), isWithFurniture(), getUseCase(), isSideInSide(), getAdvertDescription(), getFeatures());
    }
}
