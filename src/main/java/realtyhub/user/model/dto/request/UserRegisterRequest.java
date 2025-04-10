package realtyhub.user.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class UserRegisterRequest {

    private String code;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private int age;

}
