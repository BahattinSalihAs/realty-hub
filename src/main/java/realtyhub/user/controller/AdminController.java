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
@RequestMapping("/api/realty-management/admin")
@RequiredArgsConstructor
public final class AdminController {

    private final UserUpdateService userUpdateService;
    private final UserRegisterService userRegisterService;
    private final UserDeleteService userDeleteService;
    private final UserLoginService userLoginService;
    private final UserForgotPasswordService userForgotPasswordService;
    private final UserPasswordChangeService userPasswordChangeService;
    private final UserEmailVerificationService userEmailVerificationService;
    private final UserAuthenticationService userAuthenticationService;

    @PostMapping("/v1/admins-verification")
    public String sendEmailVerification(
            @RequestBody @Valid final UserEmailVerificationRequest userEmailVerificationRequest
    ){
        userEmailVerificationService.sendVerificationCodeAdminForApproval(userEmailVerificationRequest.getEmail());
        userEmailVerificationService.sendEmailVerification(userEmailVerificationRequest);

        return "Email verification sent";
    }

    @PostMapping("/v1/admins")
    public ResponseEntity<UserResponse> registerUser(
            @RequestBody @Valid final UserRegisterRequest userRegisterRequest
    ) {

        return ResponseEntity.ok(userRegisterService.registerUser(userRegisterRequest, UserRole.ADMIN));
    }

    @PatchMapping("/v1/admins/update")
    public ResponseEntity<String> updateUser(
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest
    ) {
        userUpdateService.updateUser(userUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Admin update successful");
    }

    @DeleteMapping("/v1/admins/delete")
    public ResponseEntity<String> deleteUser(
            @RequestBody @Valid final UserDeleteRequest userDeleteRequest
    ){

        userDeleteService.deleteUser(userDeleteRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Admin delete successful");

    }

    @PostMapping("/v1/login")
    public String loginAdmin(
            @RequestBody @Valid final UserLoginRequest userLoginRequest
    ){
        userLoginService.login(userLoginRequest);

        return "Admin login successful";
    }

    @PostMapping("/v1/admins/password/forgot")
    public String forgotPassword(
            @RequestBody @Valid final UserForgotPasswordRequest userForgotPasswordRequest
    ){

        userForgotPasswordService.forgotPassword(userForgotPasswordRequest);

        return "forgot password success";

    }

    @PostMapping("/v1/admins/password/change")
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
