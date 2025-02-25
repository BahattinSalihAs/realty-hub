package realtyhub.user.service;

import realtyhub.user.model.dto.request.UserLoginRequest;

public interface UserLoginService {

    void login(
            final UserLoginRequest userLoginRequest
    );
}
