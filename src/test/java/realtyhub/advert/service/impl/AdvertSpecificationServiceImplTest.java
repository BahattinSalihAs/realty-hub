package realtyhub.advert.service.impl;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import realtyhub.advert.model.dto.request.advert.AdvertFilterRequest;
import realtyhub.advert.model.entity.AdvertEntity;
import realtyhub.advert.model.entity.enums.AdvertDateRange;
import realtyhub.advert.model.entity.enums.AdvertSortType;
import realtyhub.advert.model.entity.enums.RoomType;
import realtyhub.advert.repository.AdvertRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdvertSpecificationServiceImplTest {


}