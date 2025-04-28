package realtyhub.user.controller;


import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.user.model.dto.request.*;
import realtyhub.user.model.entity.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realtyhub.user.service.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public final class CustomerController {

    private final UserUpdateService userUpdateService;
    private final UserRegisterService userRegisterService;
    private final UserDeleteService userDeleteService;
    private final UserLoginService userLoginService;
    private final UserForgotPasswordService userForgotPasswordService;
    private final UserPasswordChangeService userPasswordChangeService;
    private final UserEmailVerificationService userEmailVerificationService;
    private final FavoriteService favoriteService;


    @PostMapping("/v1/customers-verification")
    public String sendEmailVerification(
            @RequestBody @Valid final UserEmailVerificationRequest userEmailVerificationRequest
    ){
        userEmailVerificationService.sendEmailVerification(userEmailVerificationRequest);

        return "Email verification sent";
    }

    @PermitAll
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

        return "Customer login successful";
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

    // Favorite CRUD

    @PostMapping("/v1/customers/favorites/add")
    public ResponseEntity<String> addFavorite(
            @RequestBody @Valid final FavoriteRequest favoriteRequest
    ){
        favoriteService.addFavorite(favoriteRequest);

        return ResponseEntity.ok("Favorite added successfully!");
    }

    @DeleteMapping("/v1/customers/favorites/remove")
    public ResponseEntity<String> removeFavorite(
            @RequestBody @Valid final FavoriteRequest favoriteRequest
    ){
        favoriteService.removeFavorite(favoriteRequest);
        return ResponseEntity.ok("Favorite removed successfully!");
    }

    @GetMapping("/v1/customers/favorites")
    public ResponseEntity<List<AdvertEntity>> getFavorites(
            @RequestBody @Valid final FavoriteGetRequest favoriteGetRequest
    ){

        return ResponseEntity.ok(favoriteService.getFavorites(favoriteGetRequest));
    }


    
}
