package realtyhub.advert.model.dto.request.address;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressCreateRequest {
    private String city;
    private String district;
    private String neighborhood;
}
