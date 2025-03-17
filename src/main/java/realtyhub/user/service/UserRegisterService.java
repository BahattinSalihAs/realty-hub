package realtyhub.user.service;

import realtyhub.user.model.dto.request.UserRegisterRequest;
import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.entity.UserRole;

public interface UserRegisterService {


    UserResponse registerUser(
            final UserRegisterRequest userRegisterRequest,
            final UserRole userRole
    );


}
