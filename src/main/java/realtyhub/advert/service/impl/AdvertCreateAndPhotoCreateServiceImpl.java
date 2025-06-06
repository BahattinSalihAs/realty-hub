package realtyhub.advert.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import realtyhub.advert.model.dto.request.advert.AdvertCreateRequest;
import realtyhub.advert.service.AdvertCreateAndPhotoCreateService;
import realtyhub.advert.service.AdvertCreateService;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertCreateAndPhotoCreateServiceImpl implements AdvertCreateAndPhotoCreateService {
    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final AdvertCreateService advertCreateService;

    @Override
    public final ResponseEntity<String> createAdvertAndPhoto(
            final String advertJson,
            final List<MultipartFile> photos
    ) {
        try {
            final AdvertCreateRequest advertCreateRequest = objectMapper.readValue(advertJson, AdvertCreateRequest.class);

            final Set<ConstraintViolation<AdvertCreateRequest>> violations = validator.validate(advertCreateRequest);
            if (!violations.isEmpty()) {
                throw new IllegalArgumentException("Invalid JSON: " + violations.iterator().next().getMessage());
            }

            advertCreateRequest.setPhotos(photos);
            advertCreateService.createAdvert(advertCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Advert create successful");
        }catch (Exception e){
            log.error("❌ İlan oluşturulurken hata oluştu:", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sunucu hatası: İlan oluşturulamadı.");
        }


    }
}
