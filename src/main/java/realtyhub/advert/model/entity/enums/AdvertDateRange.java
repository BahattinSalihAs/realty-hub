package realtyhub.advert.model.entity.enums;

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
}
