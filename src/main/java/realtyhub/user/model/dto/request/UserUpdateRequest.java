package realtyhub.user.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.NotFound;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class UserUpdateRequest {

    @Size(min = 3, max = 20, message = "Surname must be between 3 and 20 characters!")
    private String name;

    @Size(min = 2, max = 20, message = "Surname must be between 2 and 20 characters!")
    private String surname;

    @Positive
    private int age;

    @Pattern(regexp = "^(\\+90|0)?[5][0-9]{9}$", message = "Invalid phone number!")
    private String phoneNumber;

    @Email
    @NotFound
    private String email;




}
