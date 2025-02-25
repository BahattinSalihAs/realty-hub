package realtyhub.user.service;


import realtyhub.user.model.dto.request.UserEmailVerificationRequest;

public interface UserEmailVerificationService {

    public void sendEmailVerification(
            final UserEmailVerificationRequest userEmailVerificationRequest
    );

}
