package realtyhub.advert.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import realtyhub.advert.model.dto.request.advert.*;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.enums.*;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/realty-management/advert")
public class AdvertController {
    private final AdvertUpdateService advertUpdateService;
    private final AdvertDeleteService advertDeleteService;
    private final AdvertActivationService advertActivationService;
    private final AdvertDeactivationService advertDeactivationService;
    private final AdvertFilterService advertFilterService;
    private final AdvertCreateAndPhotoCreateService advertCreateAndPhotoCreateService;
    private final AdvertRepository advertRepository;

    @GetMapping("/v1/adverts")
    final public String showAdvertForm(
            final Model model
    ){
        model.addAttribute("currencyOptions", CurrencyCode.values());
        model.addAttribute("roomTypeOptions", RoomType.values());
        model.addAttribute("heatTypeOptions", HeatType.values());
        model.addAttribute("useCaseOptions", UseCase.values());
        model.addAttribute("featureOptions", FeatureType.values());

        return "advert-create";
    }

    @PostMapping(value = "/v1/adverts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    final public ResponseEntity<String> createAdvert(
            @RequestPart(value = "advert") final String advertJson,
            @RequestPart(value = "photos", required = false) final List<MultipartFile> photos
    ) {
        advertCreateAndPhotoCreateService.createAdvertAndPhoto(advertJson, photos);

        return ResponseEntity.ok().body("Advert created");
    }

    @PatchMapping("/v1/adverts/update")
    final public ResponseEntity<String> updateAdvert(
            @Valid @RequestBody final AdvertUpdateRequest advertUpdateRequest
    ){
        advertUpdateService.updateAdvert(advertUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Advert update successful");
    }

    @GetMapping("/v1/adverts-delete")
    final public String showAdvertDeleteForm(
            final Model model
    ){
        model.addAttribute("advertDeleteForm", new AdvertDeleteRequest());
        return "advert-delete";
    }

    @PostMapping("/v1/adverts-delete")
    final public String deleteAdvert(
            @Valid @ModelAttribute("advertDeleteForm") final AdvertDeleteRequest advertDeleteRequest,
            final BindingResult bindingResult,
            final Model model
    ){
        if (bindingResult.hasErrors()) {
            return "advert-delete";
        }
        advertDeleteService.deleteAdvert(advertDeleteRequest);

        return "systems";
    }

    @PatchMapping("/v1/adverts/activate")
    final public ResponseEntity<String> activateAdvert(
            @Valid @RequestBody final AdvertActivationRequest advertActivationRequest
    ){
        advertActivationService.activateAdvert(advertActivationRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Advert activation successful");
    }

    @PatchMapping("/v1/adverts/deactivate")
    final public ResponseEntity<String> deactivateAdvert(
            @Valid @RequestBody final AdvertDeactivationRequest advertDeactivationRequest
    ){
        advertDeactivationService.deactivateAdvert(advertDeactivationRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Advert deactivation successful");
    }

    @GetMapping("/v1/adverts/filter")
    final public ResponseEntity<List<AdvertEntity>> filterAdverts(
            @RequestBody @Valid final AdvertFilterRequest advertFilterRequest
    ){

        return ResponseEntity.ok(advertFilterService.searchAdverts(advertFilterRequest));
    }

    @GetMapping("/v1/advert-list")
    final public String showAdverts(
            final Model model
    ){
        List<AdvertEntity> adverts = advertRepository.findAll();
        model.addAttribute("adverts", adverts);

        return "advert-list";
    }


}
