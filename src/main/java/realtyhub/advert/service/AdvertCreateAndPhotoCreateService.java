package realtyhub.advert.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface AdvertCreateAndPhotoCreateService {
    ResponseEntity<String> createAdvertAndPhoto(
            final String advertJson,
            final List<MultipartFile> photos
    );
}
