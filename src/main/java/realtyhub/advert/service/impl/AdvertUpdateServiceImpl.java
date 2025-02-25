package realtyhub.advert.service.impl;

import lombok.RequiredArgsConstructor;
import realtyhub.advert.model.dto.request.advert.AdvertUpdateRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.photos.PhotoEntity;
import realtyhub.advert.repository.AddressRepository;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.repository.PhotoRepository;
import realtyhub.advert.service.AdvertUpdateService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertUpdateServiceImpl implements AdvertUpdateService {
    private final AdvertRepository advertRepository;
    private final AddressRepository addressRepository;
    private final PhotoRepository photoRepository;

    @Override
    public void updateAdvert(
            final AdvertUpdateRequest request
    ) {
        AdvertEntity advertEntityFromDB = advertRepository.findByAdvertId(request.getAdvertId())
                .orElseThrow(() -> new IllegalArgumentException("Advert not found"));

        if (request.getTitle() != null) {
            advertEntityFromDB.setTitle(request.getTitle());
        }
        if (request.getAdvertPrice() != null) {
            advertEntityFromDB.setAdvertPrice(request.getAdvertPrice());
        }
        if (request.getAdvertCurrencyCode() != null) {
            advertEntityFromDB.setAdvertCurrencyCode(request.getAdvertCurrencyCode());
        }
        if (request.getAddress() != null) {
            if (request.getAddress().getCity() != null) {
                request.getAddress().setCity(request.getAddress().getCity());
            }
            if (request.getAddress().getDistrict() != null) {
                request.getAddress().setDistrict(request.getAddress().getDistrict());
            }
            if (request.getAddress().getNeighborhood() != null) {
                request.getAddress().setNeighborhood(request.getAddress().getNeighborhood());
            }
        }
        if (request.getGrossArea() > 0){
            advertEntityFromDB.setGrossArea(request.getGrossArea());
        }
        if (request.getNetArea() > 0){
            advertEntityFromDB.setNetArea(request.getNetArea());
        }
        if (request.getRoomType() != null) {
            advertEntityFromDB.setRoomType(request.getRoomType());
        }
        if (request.getBuildAge() > 0){
            advertEntityFromDB.setBuildAge(request.getBuildAge());
        }
        if (request.getFloorNumber() > 0) {
            advertEntityFromDB.setFloorNumber(request.getFloorNumber());
        }
        if (request.getTotalFloors() > 0){
            advertEntityFromDB.setTotalFloors(request.getTotalFloors());
        }
        if (request.getHeatType() != null) {
            advertEntityFromDB.setHeatType(request.getHeatType());
        }
        if (request.getBathNumber() > 0){
            advertEntityFromDB.setBathNumber(request.getBathNumber());
        }
        if (request.isBalcony()){
            advertEntityFromDB.setBalcony(true);
        }
        if (!request.isBalcony()){
            advertEntityFromDB.setBalcony(false);
        }
        if (request.isWithFurniture()){
            advertEntityFromDB.setWithFurniture(true);
        }
        if (!request.isWithFurniture()){
            advertEntityFromDB.setWithFurniture(false);
        }
        if (request.isSideInSide()){
            advertEntityFromDB.setSideInSide(true);
        }
        if (!request.isSideInSide()){
            advertEntityFromDB.setSideInSide(false);
        }
        if (request.getUseCase() != null){
            advertEntityFromDB.setUseCase(request.getUseCase());
        }
        if (request.getAdvertDescription() != null) {
            advertEntityFromDB.setAdvertDescription(request.getAdvertDescription());
        }
        if (request.getFeatures() != null) {
            advertEntityFromDB.setFeatures(request.getFeatures());
        }
        if (request.getPhotos() != null && !request.getPhotos().isEmpty()) {
            photoRepository.deleteByAdvertEntity(advertEntityFromDB);

            List<PhotoEntity> photoEntities = new ArrayList<>();
            for (MultipartFile file : request.getPhotos()) {
                String fileName = file.getOriginalFilename();
                String filePath = "uploads/" + UUID.randomUUID() + "_" + fileName;

                File destinationFile = new File(filePath);
                try {
                    file.transferTo(destinationFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                PhotoEntity photoEntity = new PhotoEntity();
                photoEntity.setAdvertEntity(advertEntityFromDB);
                photoEntity.setFilePath(filePath);

                photoEntities.add(photoEntity);
            }
            photoRepository.saveAll(photoEntities);
            advertEntityFromDB.setPhotos(photoEntities);
        }
        advertRepository.save(advertEntityFromDB);

    }
}















