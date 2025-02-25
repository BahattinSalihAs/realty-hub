package realtyhub.advert.model.dto.request.address;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateRequest {
    private String city;
    private String district;
    private String neighborhood;
}
