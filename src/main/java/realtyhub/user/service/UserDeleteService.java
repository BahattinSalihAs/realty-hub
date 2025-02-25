package realtyhub.user.service;

import realtyhub.user.model.dto.request.UserDeleteRequest;

public interface UserDeleteService {

    void deleteUser(
            final UserDeleteRequest userDeleteRequest
    );
}
