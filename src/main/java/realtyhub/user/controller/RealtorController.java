package realtyhub.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.dto.request.*;
import realtyhub.user.model.entity.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realtyhub.user.service.*;

import javax.print.attribute.standard.Media;

@Controller
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


    @GetMapping("/v1/realtors-verification")
    public String showForm(
            final Model model
    ){
        model.addAttribute("realtors", new UserEmailVerificationRequest());
        return "realtors";
    }

    @PostMapping(value = "/v1/realtors-verification", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEmailVerification(
            @RequestBody@Valid final UserEmailVerificationRequest userEmailVerificationRequest
    ){

        userEmailVerificationService.sendEmailVerification(userEmailVerificationRequest);

        return ResponseEntity.ok("OK!");
    }

    @GetMapping("/v1/realtors")
    public String showRegisterPage(
            final Model model
    ) {
        model.addAttribute("realtorsRegisterForm", new UserRegisterRequest());

        return "realtorsRegister";
    }

    @PostMapping("/v1/realtors")
    public String registerRealtor(
            @ModelAttribute("realtorsRegisterForm") @Valid final UserRegisterRequest userRegisterRequest,
            final BindingResult result,
            final Model model
    ) {

        if (result.hasErrors()){
            return "realtorsRegister";
        }
        UserResponse ur = userRegisterService.registerUser(userRegisterRequest, UserRole.REALTOR);
        model.addAttribute("message", "Hoşgeldin gayrimenkul danışmanı " + userRegisterRequest.getName() + " " + userRegisterRequest.getSurname());


        return "realtorsRegister-successed";
    }



    @PatchMapping("/v1/realtors/update")
    public ResponseEntity<String> updateRealtor(
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest
    ) {
        userUpdateService.updateUser(userUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Realtor update successful");
    }

    @GetMapping("/v1/realtors/delete")
    public String showDeleteForm(
            final Model model
    ){
        model.addAttribute("realtorsDelete", new UserDeleteRequest());
        return "realtorsDelete";
    }

    @DeleteMapping(value = "/v1/realtors/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRealtor(
            @RequestBody @Valid final UserDeleteRequest userDeleteRequest
    ){

        userDeleteService.deleteUser(userDeleteRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Realtor delete successful");

    }

    @GetMapping("/systems")
    public String systems(){

        return "systems";
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
