package realtyhub.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import realtyhub.user.model.dto.request.*;
import realtyhub.user.model.entity.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realtyhub.user.service.*;

@RestController
@RequestMapping("/api/realty-management/customer")
@RequiredArgsConstructor
public final class CustomerController {

    private final UserUpdateService userUpdateService;
    private final UserRegisterService userRegisterService;
    private final UserDeleteService userDeleteService;
    private final UserLoginService userLoginService;
    private final UserForgotPasswordService userForgotPasswordService;
    private final UserPasswordChangeService userPasswordChangeService;
    private final UserEmailVerificationService userEmailVerificationService;

    @PostMapping("/v1/customers-verification")
    public String sendEmailVerification(
            @RequestBody @Valid final UserEmailVerificationRequest userEmailVerificationRequest
    ){
        userEmailVerificationService.sendEmailVerification(userEmailVerificationRequest);

        return "Email verification sent";
    }

    @PostMapping("/v1/customers")
    public ResponseEntity<String> registerUser(
            @RequestBody @Valid final UserRegisterRequest userRegisterRequest
    ) {
        userRegisterService.registerUser(userRegisterRequest, UserRole.CUSTOMER);

        return ResponseEntity.status(HttpStatus.CREATED).body("Customer register successful");
    }



    @PatchMapping("/v1/customers/update")
    public ResponseEntity<String> updateUser(
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest
    ) {
        userUpdateService.updateUser(userUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Customer update successful");
    }


    @DeleteMapping("/v1/customers/delete")
    public ResponseEntity<String> deleteUser(
            @RequestBody @Valid final UserDeleteRequest userDeleteRequest
    ){

        userDeleteService.deleteUser(userDeleteRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Customer delete successful");

    }

    @PostMapping("/v1/login")
    public String loginCustomer(
            @RequestBody @Valid final UserLoginRequest userLoginRequest
    ){

        userLoginService.login(userLoginRequest);

        return "customer login success";

    }

    @PostMapping("/v1/customers/password/forgot")
    public String forgotPassword(
            @RequestBody @Valid final UserForgotPasswordRequest userForgotPasswordRequest
    ){

        userForgotPasswordService.forgotPassword(userForgotPasswordRequest);

        return "forgot password success";

    }

    @PostMapping("/v1/customers/password/change")
    public String changePassword(
            @RequestBody @Valid final UserPasswordChangeRequest userPasswordChangeRequest
    ){
        userPasswordChangeService.changePassword(userPasswordChangeRequest);

        return "password change success";
    }


    
}
