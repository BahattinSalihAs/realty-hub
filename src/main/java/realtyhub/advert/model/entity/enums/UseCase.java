package realtyhub.advert.model.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UseCase {
    TENANTED("Kiracılı"),
    EMPTY("Boş"),
    OWNER_OCCUPIED("Ev Sahibi");

    private final String label;

    UseCase(
            final String label
    ) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static UseCase fromString(
            final String value
    ) {
        for (final UseCase useCase : UseCase.values()) {
            if (useCase.label.equalsIgnoreCase(value)) {
                return useCase;
            }
        }
        throw new IllegalArgumentException("Invalid use case: " + value);
    }
}
