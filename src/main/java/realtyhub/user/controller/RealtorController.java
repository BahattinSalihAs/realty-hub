package realtyhub.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import realtyhub.common.service.CodeSenderService;
import realtyhub.common.service.EmailSenderService;
import realtyhub.user.model.dto.request.*;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    public static String adminCode;

    @PostMapping("/v1/realtors-verification")
    public String sendEmailVerification(
            @RequestBody @Valid final UserEmailVerificationRequest userEmailVerificationRequest
    ){
        UserEntity userEntityFromDB = userRepository.findUserEntityByUserRole(UserRole.ADMIN);
        adminCode = String.valueOf(CodeSenderService.sendCode());
        emailSenderService.sendEmail(userEntityFromDB.getEmail(), "VERIFY CODE REALTOR", "Code: " + adminCode + " \nfor " + userEmailVerificationRequest.getEmail() + " send if safe user!");
        userEmailVerificationService.sendEmailVerification(userEmailVerificationRequest);

        return "Email verification sent";
    }

    @PostMapping("/v1/realtors")
    public ResponseEntity<String> registerUser(
            @RequestBody @Valid final UserRegisterRequest userRegisterRequest
    ) {
        userRegisterService.registerUser(userRegisterRequest, UserRole.REALTOR);

        return ResponseEntity.status(HttpStatus.CREATED).body("Realtor register successful");
    }



    @PatchMapping("/v1/realtors/update")
    public ResponseEntity<String> updateUser(
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest
    ) {
        userUpdateService.updateUser(userUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Realtor update successful");
    }


    @DeleteMapping("/v1/realtors/delete")
    public ResponseEntity<String> deleteUser(
            @RequestBody @Valid final UserDeleteRequest userDeleteRequest
    ){

        userDeleteService.deleteUser(userDeleteRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Realtor delete successful");

    }

    @PostMapping("/v1/login")
    public String loginCustomer(
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
}
