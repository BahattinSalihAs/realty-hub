package realtyhub.advert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import realtyhub.advert.model.dto.request.advert.AdvertCreateRequest;
import realtyhub.advert.model.dto.request.advert.AdvertDeleteRequest;
import realtyhub.advert.model.dto.request.advert.AdvertUpdateRequest;
import realtyhub.advert.service.AdvertCreateService;
import realtyhub.advert.service.AdvertDeleteService;
import realtyhub.advert.service.AdvertUpdateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/realty-management/advert")
public class AdvertController {
    private final AdvertCreateService advertCreateService;
    private final AdvertUpdateService advertUpdateService;
    private final AdvertDeleteService advertDeleteService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @PostMapping(value = "/v1/adverts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    final public ResponseEntity<String> createAdvert(
            @RequestPart(value = "advert") final String advertJson,
            @RequestPart(value = "photos", required = false) final List<MultipartFile> photos
            ) {
        try {
            AdvertCreateRequest advertCreateRequest = objectMapper.readValue(advertJson, AdvertCreateRequest.class);

            Set<ConstraintViolation<AdvertCreateRequest>> violations = validator.validate(advertCreateRequest);
            if (!violations.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid advert JSON: " + violations.iterator().next().getMessage());
            }

            advertCreateRequest.setPhotos(photos);
            advertCreateService.createAdvert(advertCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Advert create successful");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }



    }

    @PatchMapping("/v1/adverts/update")
    final public ResponseEntity<String> updateAdvert(
            @Valid @RequestBody final AdvertUpdateRequest advertUpdateRequest
    ){
        advertUpdateService.updateAdvert(advertUpdateRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Advert update successful");
    }

    @DeleteMapping("/v1/adverts/delete")
    final public ResponseEntity<String> deleteAdvert(
            @Valid @RequestBody final AdvertDeleteRequest advertDeleteRequest
    ){
        advertDeleteService.deleteAdvert(advertDeleteRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Advert delete successful");
    }


}
