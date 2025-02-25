package realtyhub.user.model.dto.request;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailVerificationRequest {
    private String email;
}
