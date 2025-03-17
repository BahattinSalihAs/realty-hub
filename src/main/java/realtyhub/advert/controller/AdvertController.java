package realtyhub.advert.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import realtyhub.advert.model.dto.request.advert.*;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/realty-management/advert")
public class AdvertController {
    private final AdvertUpdateService advertUpdateService;
    private final AdvertDeleteService advertDeleteService;
    private final AdvertActivationService advertActivationService;
    private final AdvertDeactivationService advertDeactivationService;
    private final AdvertFilterService advertFilterService;
    private final AdvertCreateAndPhotoCreateService advertCreateAndPhotoCreateService;

    @PostMapping(value = "/v1/adverts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    final public ResponseEntity<String> createAdvert(
            @RequestPart(value = "advert") final String advertJson,
            @RequestPart(value = "photos", required = false) final List<MultipartFile> photos
    ) {

        return advertCreateAndPhotoCreateService.createAdvertAndPhoto(advertJson, photos);
    }

    @PatchMapping("/v1/adverts/update")
    final public ResponseEntity<String> updateAdvert(
            @Valid @RequestBody final AdvertUpdateRequest advertUpdateRequest
    ){
        advertUpdateService.updateAdvert(advertUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Advert update successful");
    }

    @DeleteMapping("/v1/adverts")
    final public ResponseEntity<String> deleteAdvert(
            @Valid @RequestBody final AdvertDeleteRequest advertDeleteRequest
    ){
        advertDeleteService.deleteAdvert(advertDeleteRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Advert delete successful");
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


}
