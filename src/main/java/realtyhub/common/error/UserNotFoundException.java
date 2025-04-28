package realtyhub.common.error;

import java.io.Serial;

public final class UserNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1484608220633876023L;

    public static final String DEFAULT_MESSAGE = "User not found";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(final String message) {
        super(DEFAULT_MESSAGE + " " +message);
    }
}
