package realtyhub.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.dto.request.*;
import realtyhub.user.model.entity.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realtyhub.user.service.*;

@RestController
@RequestMapping("/api/realty-management/realtor")
@RequiredArgsConstructor
public final class RealtorController {

    private final UserUpdateService userUpdateService;
    private final UserRegisterService userRegisterService;
    private final UserDeleteService userDeleteService;
    private final UserLoginService userLoginService;
    private final UserForgotPasswordService userForgotPasswordService;
    private final UserPasswordChangeService userPasswordChangeService;
    private final UserEmailVerificationService userEmailVerificationService;
    private final UserAuthenticationService userAuthenticationService;


    @PostMapping("/v1/realtors-verification")
    public String sendEmailVerification(
            @RequestBody @Valid final UserEmailVerificationRequest userEmailVerificationRequest
    ){
        userEmailVerificationService.sendVerificationCodeRealtorForAdminApproval(userEmailVerificationRequest.getEmail());
        userEmailVerificationService.sendEmailVerification(userEmailVerificationRequest);

        return "Email verification sent";
    }

    @PostMapping("/v1/realtors")
    public ResponseEntity<UserResponse> registerRealtor(
            @RequestBody @Valid final UserRegisterRequest userRegisterRequest
    ) {

        return ResponseEntity.ok(userRegisterService.registerUser(userRegisterRequest, UserRole.REALTOR));
    }



    @PatchMapping("/v1/realtors/update")
    public ResponseEntity<String> updateRealtor(
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest
    ) {
        userUpdateService.updateUser(userUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Realtor update successful");
    }


    @DeleteMapping("/v1/realtors/delete")
    public ResponseEntity<String> deleteRealtor(
            @RequestBody @Valid final UserDeleteRequest userDeleteRequest
    ){

        userDeleteService.deleteUser(userDeleteRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Realtor delete successful");

    }

    @PostMapping("/v1/login")
    public String loginRealtor(
            @RequestBody @Valid final UserLoginRequest userLoginRequest
    ){

        userLoginService.login(userLoginRequest);

        return "realtor login success";

    }

    @PostMapping("/v1/realtors/password/forgot")
    public String forgotPassword(
            @RequestBody @Valid final UserForgotPasswordRequest userForgotPasswordRequest
    ){

        userForgotPasswordService.forgotPassword(userForgotPasswordRequest);

        return "forgot password success";

    }

    @PostMapping("/v1/realtors/password/change")
    public String changePassword(
            @RequestBody @Valid final UserPasswordChangeRequest userPasswordChangeRequest
    ){
        userPasswordChangeService.changePassword(userPasswordChangeRequest);

        return "password change success";
    }

    @PostMapping("/v1/auth")
    public final ResponseEntity<UserResponse> authenticate(
            @RequestBody @Valid final UserAuthenticationRequest userAuthenticationRequest
    ){

        return ResponseEntity.ok(userAuthenticationService.authenticate(userAuthenticationRequest));
    }
}
