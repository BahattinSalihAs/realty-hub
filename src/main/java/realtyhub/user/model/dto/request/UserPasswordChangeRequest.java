package realtyhub.user.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UserPasswordChangeRequest {

    private String email;

    private String otpCode;

    private String newPassword;

}
