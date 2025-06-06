package realtyhub.user.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    private String email;

    private String password;
}
