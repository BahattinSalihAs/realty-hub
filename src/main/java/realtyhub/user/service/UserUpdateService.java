package realtyhub.user.service;


import realtyhub.user.model.dto.request.UserUpdateRequest;

public interface UserUpdateService {


    void updateUser(
            final UserUpdateRequest userUpdateRequest
    );

}
