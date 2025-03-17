package realtyhub.user.model.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequest {
    private String email;
    private List<Long> advertIdList;
}
