package realtyhub.user.service;

import realtyhub.user.model.dto.request.UserRegisterRequest;
import realtyhub.user.model.entity.UserRole;

public interface UserRegisterService {


    void registerUser(
            final UserRegisterRequest userRegisterRequest,
            final UserRole userRole
    );


}
