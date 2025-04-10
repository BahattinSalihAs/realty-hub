package realtyhub.user.service;


import realtyhub.user.model.dto.request.UserEmailVerificationRequest;
import realtyhub.user.model.entity.VerificationCode;

public interface UserEmailVerificationService {

    void sendEmailVerification(
            final UserEmailVerificationRequest userEmailVerificationRequest
    );

    void sendVerificationCodeRealtorForAdminApproval(
            final String email
    );

}
