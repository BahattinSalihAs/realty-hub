package realtyhub.user.service;

import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.dto.request.UserAuthenticationRequest;

public interface UserAuthenticationService {

    UserResponse authenticate(
            final UserAuthenticationRequest userAuthenticationRequest
            );
}
