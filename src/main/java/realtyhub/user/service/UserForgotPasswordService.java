package realtyhub.user.service;

import realtyhub.user.model.dto.request.UserForgotPasswordRequest;

public interface UserForgotPasswordService {

    void forgotPassword(
            final UserForgotPasswordRequest userForgotPasswordRequest
    );
}
