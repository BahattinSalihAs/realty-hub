package realtyhub.advert.model.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdvertDateRange {
    LAST_24_HOURS(1),
    LAST_3_DAYS(3),
    LAST_7_DAYS(7),
    LAST_15_DAYS(15),
    LAST_30_DAYS(30);

    private final int days;

    @JsonCreator
    public static AdvertDateRange fromValue(
            final int value
    ){
        for (AdvertDateRange advertDateRange : AdvertDateRange.values()) {
            if (advertDateRange.days == value) {
                return advertDateRange;
            }
        }
        throw new IllegalArgumentException("Invalid AdvertDateRange: " + value);
    }

}
