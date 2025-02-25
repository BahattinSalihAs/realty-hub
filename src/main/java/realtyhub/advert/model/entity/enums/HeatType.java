package realtyhub.advert.model.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum HeatType {
    CENTRAL_HEATING("Merkezi Sistem"),
    COMBI_BOILER("Kombi"),
    STOVE("Soba"),
    FLOOR_HEATING("Yerden Isıtma"),
    AIR_CONDITIONING("Klima"),
    NONE("Isıtıma Yok");

    private final String label;

    HeatType(
            final String label
    ) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static HeatType fromString(
            final String label
    ) {
        for (final HeatType heatType : HeatType.values()) {
            if (heatType.label.equalsIgnoreCase(label)) {
                return heatType;
            }
        }
        throw new IllegalArgumentException(label + " is not a valid enum value");
    }
}
