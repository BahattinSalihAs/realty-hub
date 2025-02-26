package realtyhub.advert.model.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AdvertSortType {
    PRICE_ASC("advertPrice", Sort.Direction.ASC),
    PRICE_DESC("advertPrice", Sort.Direction.DESC),
    DATE_NEWEST("advertDate", Sort.Direction.DESC),
    DATE_OLDEST("advertDate", Sort.Direction.ASC);

    private final String field;
    private final Sort.Direction direction;

    @JsonCreator
    public static AdvertSortType fromValue(
            final String value
    ){
        return AdvertSortType.valueOf(value.toUpperCase());
    }

}
