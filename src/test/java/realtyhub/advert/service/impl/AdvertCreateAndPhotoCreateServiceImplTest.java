package realtyhub.advert.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import realtyhub.advert.model.dto.request.advert.AdvertCreateRequest;
import realtyhub.advert.service.AdvertCreateService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdvertCreateAndPhotoCreateServiceImplTest {

    @InjectMocks
    private AdvertCreateAndPhotoCreateServiceImpl advertCreateAndPhotoCreateServiceImpl;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Validator validator;

    @Mock
    private AdvertCreateService advertCreateService;

    @Test
    void testCreateAdvertAndPhotoWithValidJson() throws Exception {

        final String advertJson = "{ \\\"title\\\": \\\"Sample Advert\\\" }";
        MultipartFile photo = Mockito.mock(MultipartFile.class);
        final AdvertCreateRequest advertCreateRequest = new AdvertCreateRequest();

        Mockito.when(objectMapper.readValue(advertJson, AdvertCreateRequest.class)).thenReturn(advertCreateRequest);
        Mockito.when(validator.validate(advertCreateRequest)).thenReturn(Collections.emptySet());

        ResponseEntity<String> response = advertCreateAndPhotoCreateServiceImpl.createAdvertAndPhoto(advertJson, Collections.singletonList(photo));

        //assert
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals("Advert create successful", response.getBody());

        //verify
        Mockito.verify(objectMapper).readValue(advertJson, AdvertCreateRequest.class);
        Mockito.verify(validator).validate(advertCreateRequest);
    }
}