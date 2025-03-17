package realtyhub.user.model.dto.request;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationRequest {

    private String email;
    private String password;
}
