package com.levi.manager.util;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.domain.User;
import com.levi.manager.domain.parent.LocalizedEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class DistanceCalculatorUtilTest {

    private final Double FIRST_LOCALIZED_ENTITY_LATITUDE = 20.0;
    private final Double FIRST_LOCALIZED_ENTITY_LONGITUDE = 15.0;
    private final Double SECOND_LOCALIZED_ENTITY_LATITUDE = 20.0;
    private final Double SECOND_LOCALIZED_ENTITY_LONGITUDE = 12.0;

    private final Double DELTA = 1e-15;

    @Test
    public void calculateDistanceBetweenPoints() {
        Assert.assertEquals(DistanceCalculatorUtil.calculateDistanceBetweenPoints(
                givenFirstLocalizedEntity(), givenSecondLocalizedEntity()), 313.46296639187625, DELTA);
    }

    private LocalizedEntity givenFirstLocalizedEntity() {
        LocalizedEntity localizedEntity = new Restaurant();
        localizedEntity.setLatitude(FIRST_LOCALIZED_ENTITY_LATITUDE);
        localizedEntity.setLongitude(FIRST_LOCALIZED_ENTITY_LONGITUDE);
        return localizedEntity;
    }

    private LocalizedEntity givenSecondLocalizedEntity() {
        LocalizedEntity localizedEntity = new User();
        localizedEntity.setLatitude(SECOND_LOCALIZED_ENTITY_LATITUDE);
        localizedEntity.setLongitude(SECOND_LOCALIZED_ENTITY_LONGITUDE);
        return localizedEntity;
    }

}
