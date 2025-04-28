package realtyhub.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import realtyhub.user.model.dto.UserResponse;
import realtyhub.user.model.dto.request.*;
import realtyhub.user.model.entity.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realtyhub.user.service.*;

@Controller
@RequestMapping("/admin")
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
    public ModelAndView showForm(){
        final ModelAndView modelAndView = new ModelAndView("emailVerification");
        modelAndView.addObject("emailVerification", new UserEmailVerificationRequest());
        return modelAndView;
    }

    @PostMapping("/v1/admins-verification")
    public ModelAndView sendEmailVerification(
            @ModelAttribute("emailVerification")@Valid final UserEmailVerificationRequest userEmailVerificationRequest
    ){

        userEmailVerificationService.sendEmailVerification(userEmailVerificationRequest);
        return new ModelAndView("redirect:/admin/v1/admins");
    }

    @GetMapping("/v1/admins")
    public ModelAndView showRegisterPage(){
        final ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("registerForm", new UserRegisterRequest());
        return modelAndView;
    }

    @PostMapping("/v1/admins")
    public ModelAndView registerUser(
            @ModelAttribute("registerForm")@Valid final UserRegisterRequest userRegisterRequest,
            final BindingResult result
    ) {
        if (result.hasErrors()){
            return new ModelAndView("register");
        }

        final UserResponse ur = userRegisterService.registerUser(userRegisterRequest, UserRole.ADMIN);
        final ModelAndView modelAndView = new ModelAndView("register-successed");
        modelAndView.addObject("nameSurname", "Hoşgeldin " + userRegisterRequest.getName() + " " + userRegisterRequest.getSurname());
        modelAndView.addObject("token", ur.getToken());
        return modelAndView;
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
