package realtyhub.user.service;

import realtyhub.user.model.dto.request.UserPasswordChangeRequest;

public interface UserPasswordChangeService {

    void changePassword(
            final UserPasswordChangeRequest userPasswordChangeRequest
    );
}
