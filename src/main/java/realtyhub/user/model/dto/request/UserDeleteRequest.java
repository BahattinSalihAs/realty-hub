package realtyhub.user.model.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UserDeleteRequest {

    private String email;

    private String password;
}
