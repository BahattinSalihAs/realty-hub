package realtyhub.advert.model.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RoomType {
    ONE_PLUS_ZERO("1+0"),
    ONE_PLUS_ONE("1+1"),
    ONE_AND_HALF_PLUS_ONE("1.5+1"),
    TWO_PLUS_ZERO("2+0"),
    TWO_PLUS_ONE("2+1"),
    TWO_AND_HALF_PLUS_ONE("2.5+1"),
    TWO_PLUS_TWO("2+2"),
    THREE_PLUS_ONE("3+1"),
    THREE_AND_HALF_PLUS_ONE("3.5+1"),
    THREE_PLUS_TWO("3+2"),
    FOUR_PLUS_ONE("4+1"),
    FOUR_AND_HALF_PLUS_ONE("4.5+1"),
    FOUR_PLUS_TWO("4+2"),
    FIVE_PLUS_ONE("5+1"),
    FIVE_PLUS_TWO("5+2"),
    SIX_PLUS_ONE("6+1"),
    SIX_PLUS_TWO("6+2");

    private final String label;

    RoomType(
            final String label
    ) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static RoomType fromString(
            final String value
    ) {
        for (final RoomType type : RoomType.values()) {
            if (type.label.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid room type: " + value);
    }

}










