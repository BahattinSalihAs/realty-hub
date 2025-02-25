package realtyhub.advert.model.dto.request.advert;


import lombok.*;
import realtyhub.advert.model.dto.request.address.AddressCreateRequest;
import realtyhub.advert.model.entity.enums.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvertCreateRequest {

    private String title;

    private BigDecimal advertPrice;

    private CurrencyCode advertCurrencyCode;

    private AddressCreateRequest addressEntity;

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
