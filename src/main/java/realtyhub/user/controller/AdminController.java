package realtyhub.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@Controller
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

    @GetMapping("/v1/admins-verification")
    public String showForm(
            final Model model
    ){
        model.addAttribute("emailVerification", new UserEmailVerificationRequest());
        return "emailVerification";
    }

    @PostMapping("/v1/admins-verification")
    public String sendEmailVerification(
            @ModelAttribute("emailVerification")@Valid final UserEmailVerificationRequest userEmailVerificationRequest,
            final BindingResult result,
            final Model model
    ){

        if (result.hasErrors()){
            return "emailVerification";
        }
        userEmailVerificationService.sendEmailVerification(userEmailVerificationRequest);

        model.addAttribute("message", "Email verification sent");
        return "redirect:/api/realty-management/admin/v1/admins";
    }

    @GetMapping("/v1/admins")
    public String showRegisterPage(
            final Model model
    ){
        model.addAttribute("registerForm", new UserRegisterRequest());
        return "register";
    }

    @PostMapping("/v1/admins")
    public String registerUser(
            @ModelAttribute("registerForm")@Valid final UserRegisterRequest userRegisterRequest,
            final BindingResult result,
            final Model model
    ) {
        if (result.hasErrors()){
            return "register";
        }

        UserResponse ur = userRegisterService.registerUser(userRegisterRequest, UserRole.ADMIN);
        model.addAttribute("nameSurname", "Hoşgeldin " + userRegisterRequest.getName() + " " + userRegisterRequest.getSurname());
        model.addAttribute("token", ur.getToken());
        return "register-successed";
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
    @GetMapping("/v1/login")
    public String showLoginPage(
            final Model model
    ){
        model.addAttribute("loginForm", new UserLoginRequest());
        return "login";
    }

    @GetMapping("/v1/login-success")
    public String showLoginSuccess(
            final Model model
    ){
        model.addAttribute("message", "Login successful");

        return "login-successed";
    }

    @GetMapping("/leaders")
    public String showLeadersPage(){
        return "leaders";
    }

    @GetMapping("/accessDenied")
    public String showAccessDeniedPage() {

        return "accessDenied";
    }

    /*
    @PostMapping("/v1/login")
    public String loginAdmin(
            @ModelAttribute("loginForm") @Valid final UserLoginRequest userLoginRequest,
            final BindingResult result,
            final Model model
    ){
        if (result.hasErrors()){
            return "login";
        }

        model.addAttribute("message", "Admin login successful");
        userLoginService.login(userLoginRequest);
        return "login-successed";
    }

     */

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
