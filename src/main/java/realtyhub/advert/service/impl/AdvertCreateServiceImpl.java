package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.advert.model.dto.request.advert.AdvertCreateRequest;
import realtyhub.advert.model.entity.AddressEntity;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.photos.PhotoEntity;
import realtyhub.advert.repository.AddressRepository;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.repository.PhotoRepository;
import realtyhub.advert.service.AdvertCreateService;
import realtyhub.advert.service.AdvertNumberGenerateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
class AdvertCreateServiceImpl implements AdvertCreateService {

    private final AdvertRepository advertRepository;
    private final AddressRepository addressRepository;
    private final AdvertNumberGenerateService advertNumberGenerateService;
    private final PhotoRepository photoRepository;
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @Transactional
    @Override
    public void createAdvert(
            final AdvertCreateRequest advertCreateRequest
    ) {
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        AddressEntity address = AddressEntity.builder()
                .city(advertCreateRequest.getAddressEntity().getCity())
                .district(advertCreateRequest.getAddressEntity().getDistrict())
                .neighborhood(advertCreateRequest.getAddressEntity().getNeighborhood())
                .build();

        final AdvertEntity advert = AdvertEntity.builder()
                .title(advertCreateRequest.getTitle())
                .advertPrice(advertCreateRequest.getAdvertPrice())
                .advertCurrencyCode(advertCreateRequest.getAdvertCurrencyCode())
                .addressEntity(address)
                .grossArea(advertCreateRequest.getGrossArea())
                .netArea(advertCreateRequest.getNetArea())
                .roomType(advertCreateRequest.getRoomType())
                .buildAge(advertCreateRequest.getBuildAge())
                .floorNumber(advertCreateRequest.getFloorNumber())
                .totalFloors(advertCreateRequest.getTotalFloors())
                .heatType(advertCreateRequest.getHeatType())
                .bathNumber(advertCreateRequest.getBathNumber())
                .isBalcony(advertCreateRequest.isBalcony())
                .isWithFurniture(advertCreateRequest.isWithFurniture())
                .useCase(advertCreateRequest.getUseCase())
                .isSideInSide(advertCreateRequest.isSideInSide())
                .advertDescription(advertCreateRequest.getAdvertDescription())
                .advertId(advertNumberGenerateService.generateAdvertNumber())
                .advertDate(LocalDate.now())
                .features(advertCreateRequest.getFeatures())
                .isActive(true)
                .build();

        address.setAdvertEntity(advert);
        advertRepository.save(advert);
        addressRepository.save(address);

        if (advertCreateRequest.getPhotos() != null && !advertCreateRequest.getPhotos().isEmpty()) {
            List<PhotoEntity> photoEntities = new ArrayList<>();

            for (MultipartFile file : advertCreateRequest.getPhotos()) {
                try {
                    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir + fileName);

                    Files.copy(file.getInputStream(), filePath);

                    PhotoEntity photo = PhotoEntity.builder()
                            .advertEntity(advert)
                            .filePath(filePath.toString())
                            .build();
                    photoEntities.add(photo);
                }catch (Exception e){
                    throw new RuntimeException("Failed to upload photo: " + e.getMessage(), e);
                }
            }
            photoRepository.saveAll(photoEntities);
            advert.setPhotos(photoEntities);
            advertRepository.save(advert);
        }

    }
}
