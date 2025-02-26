package realtyhub.advert.model.dto.request.advert;

import lombok.*;
import realtyhub.advert.model.entity.enums.AdvertDateRange;
import realtyhub.advert.model.entity.enums.AdvertSortType;
import realtyhub.advert.model.entity.enums.RoomType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertFilterRequest {
    private String city;
    private String district;
    private String neighborhood;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minArea;
    private Integer maxArea;
    private List<RoomType> roomTypes;
    private AdvertDateRange advertDateRange;

    private AdvertSortType advertSortType;
}
