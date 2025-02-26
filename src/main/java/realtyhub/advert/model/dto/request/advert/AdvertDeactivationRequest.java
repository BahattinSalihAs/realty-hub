package realtyhub.advert.model.dto.request.advert;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertDeactivationRequest {
    private long advertId;
}
