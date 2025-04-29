package realtyhub.advert.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import realtyhub.advert.model.dto.request.address.AddressCreateRequest;
import realtyhub.advert.model.dto.request.advert.AdvertCreateRequest;
import realtyhub.advert.model.entity.AddressEntity;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.enums.*;
import realtyhub.advert.model.entity.photos.PhotoEntity;
import realtyhub.advert.repository.AddressRepository;
import realtyhub.advert.repository.AdvertRepository;
import realtyhub.advert.repository.PhotoRepository;
import realtyhub.advert.service.AdvertNumberGenerateService;
import realtyhub.user.model.entity.UserEntity;
import realtyhub.user.model.entity.UserRole;
import realtyhub.user.repository.UserRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class AdvertCreateServiceImplTest {

    @InjectMocks
    private AdvertCreateServiceImpl advertCreateServiceImpl;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AdvertNumberGenerateService advertNumberGenerateService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhotoRepository photoRepository;

    private String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @Test
    public void shouldAllowedAdvertCreationForRealtor(){

        final String mockedEmail = "test@example.com";

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(mockedEmail);

        SecurityContextHolder.setContext(securityContext);

        final String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<MultipartFile> files;
        String filePaths = "src/main/resources/static/konumm.png";
        Path path = Paths.get(filePaths);
        try {
            byte[] content = Files.readAllBytes(path);
            MultipartFile mockFile = new MockMultipartFile(
                    "file",
                    path.getFileName().toString(),
                    "image/png",
                    content
            ) {
            };
            files = new ArrayList<>();
            files.add(mockFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AddressEntity addressEntity = AddressEntity.builder()
                .city("city")
                .district("district")
                .neighborhood("neighborhood")
                .build();
        Mockito.when(addressRepository.save(Mockito.any(AddressEntity.class))).thenReturn(addressEntity);

        AddressCreateRequest addressCreateRequest = AddressCreateRequest.builder()
                .city(addressEntity.getCity())
                .district(addressEntity.getDistrict())
                .neighborhood(addressEntity.getNeighborhood())
                .build();

        UserEntity userEntity = UserEntity.builder()
                .userRole(UserRole.REALTOR)
                .age(30)
                .email(email)
                .password("password")
                .name("Ahmet")
                .surname("Yılmaz")
                .phoneNumber("05336542311")
                .lastPasswordChange(LocalDate.now().plusDays(180))
                .build();

        Mockito.when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(userEntity));

        AdvertCreateRequest advertCreateRequest = AdvertCreateRequest.builder()
                .advertDescription("Test advert description")
                .advertPrice(BigDecimal.valueOf(25000))
                .advertCurrencyCode(CurrencyCode.TRY)
                .title("Test advert title")
                .grossArea(90)
                .netArea(60)
                .roomType(RoomType.ONE_PLUS_ONE)
                .buildAge(5)
                .floorNumber(3)
                .totalFloors(7)
                .heatType(HeatType.FLOOR_HEATING)
                .totalBathNumber(2)
                .isBalcony(true)
                .isWithFurniture(false)
                .useCase(UseCase.TENANTED)
                .isSideInSide(false)
                .features(Collections.singletonList(FeatureType.DIS_GUVENLIK))
                .addressEntity(addressCreateRequest)
                .photos(files)
                .build();

        AdvertNumberGenerateServiceImpl numbers = new AdvertNumberGenerateServiceImpl(advertRepository);

        final AdvertEntity advert = AdvertEntity.builder()
                .realtor(userEntity)
                .advertDescription(advertCreateRequest.getAdvertDescription())
                .advertPrice(advertCreateRequest.getAdvertPrice())
                .advertCurrencyCode(advertCreateRequest.getAdvertCurrencyCode())
                .title(advertCreateRequest.getTitle())
                .advertId(numbers.generateAdvertNumber())
                .grossArea(advertCreateRequest.getGrossArea())
                .netArea(advertCreateRequest.getNetArea())
                .roomType(advertCreateRequest.getRoomType())
                .totalFloors(advertCreateRequest.getTotalFloors())
                .totalBathNumber(advertCreateRequest.getTotalBathNumber())
                .isBalcony(advertCreateRequest.isBalcony())
                .isWithFurniture(advertCreateRequest.isWithFurniture())
                .useCase(advertCreateRequest.getUseCase())
                .isSideInSide(advertCreateRequest.isSideInSide())
                .features(advertCreateRequest.getFeatures())
                .advertDate(LocalDate.now())
                .isActive(true)
                .addressEntity(addressEntity)
                .buildAge(advertCreateRequest.getBuildAge())
                .floorNumber(advertCreateRequest.getFloorNumber())
                .heatType(advertCreateRequest.getHeatType())
                .build();


        List<PhotoEntity> photoEntities = new ArrayList<>();
        for (MultipartFile file : advertCreateRequest.getPhotos()) {
            final String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            PhotoEntity photoEntity = PhotoEntity.builder()
                    .advertEntity(advert)
                    .filePath(filePath.toString())
                    .build();
            photoEntities.add(photoEntity);
        }

        System.out.println(photoEntities);

        Mockito.when(photoRepository.saveAll(Mockito.anyList())).thenReturn(photoEntities);
        advert.setPhotos(photoEntities);
        Mockito.when(advertRepository.save(Mockito.any(AdvertEntity.class))).thenReturn(advert);


        Assertions.assertEquals(advert.getRealtor(), userEntity);
        Assertions.assertEquals(advert.getPhotos(), photoEntities);
        Assertions.assertEquals(advert.getAddressEntity(), addressEntity);
        Assertions.assertEquals(advert, advertCreateRequest);
        Assertions.assertEquals(mockedEmail, email);
        Assertions.assertTrue(advert.isActive());

        advertCreateServiceImpl.createAdvert(advertCreateRequest);

        Mockito.verify(userRepository).findByEmail(userEntity.getEmail());
        Mockito.verify(addressRepository).save(Mockito.any(AddressEntity.class));
        Mockito.verify(advertRepository, Mockito.times(2)).save(Mockito.any(AdvertEntity.class));
        Mockito.verify(photoRepository).saveAll(Mockito.anyList());

    }
}