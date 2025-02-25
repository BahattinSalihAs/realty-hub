package realtyhub.user.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UserForgotPasswordRequest {

    private String email;

}
