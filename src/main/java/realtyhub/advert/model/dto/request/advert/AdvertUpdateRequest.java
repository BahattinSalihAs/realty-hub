package realtyhub.advert.model.dto.request.advert;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import realtyhub.advert.model.dto.request.address.AddressUpdateRequest;
import org.springframework.web.multipart.MultipartFile;
import realtyhub.advert.model.entity.enums.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertUpdateRequest {

    @NotNull
    private long advertId;

    private String title;
    private BigDecimal advertPrice;
    private CurrencyCode advertCurrencyCode;
    private AddressUpdateRequest address;
    private int grossArea;
    private int netArea;
    private RoomType roomType;
    private int buildAge;
    private int floorNumber;
    private int totalFloors;
    private HeatType heatType;
    private int bathNumber;
    private boolean isBalcony;
    private boolean isWithFurniture;
    private UseCase useCase;
    private boolean isSideInSide;
    private String advertDescription;
    private List<FeatureType> features;
    private List<MultipartFile> photos;

}
