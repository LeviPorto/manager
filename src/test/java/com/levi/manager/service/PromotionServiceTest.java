package com.levi.manager.service;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.Promotion;
import com.levi.manager.domain.enumeration.FoodCategory;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.repository.PromotionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class PromotionServiceTest {

    @InjectMocks
    private PromotionService service;

    @Mock
    private PromotionRepository repository;

    @Mock
    private RestaurantService restaurantService;

    private final String USER_CITY = "Test City";
    private final String SEARCHED_NAME = "Promotion 1";
    private final Integer USER_ID = 1;

    private final String COMBO_NAME = "Promotion 1";
    private final String SECOND_COMBO_NAME = "Promotion 2";
    private final String THIRD_COMBO_NAME = "Promotion 3";

    private final Integer RESTAURANT_ID = 1;

    @Test
    public void retrieveFilteredPromotionsWithoutSearchedName() {
        BDDMockito.given(restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(USER_CITY).userId(USER_ID).build())).willReturn(givenFilteredRestaurants());
        List<Promotion> filteredPromotions = service.retrieveFilteredPromotions(null , USER_CITY, USER_ID);
        Assert.assertEquals(givenPromotions(), filteredPromotions);
    }

    @Test
    public void retrieveFilteredPromotionsWithSearchedName() {
        BDDMockito.given(restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(USER_CITY).userId(USER_ID).build())).willReturn(givenFilteredRestaurants());
        List<Promotion> filteredPromotions = service.retrieveFilteredPromotions(SEARCHED_NAME , USER_CITY, USER_ID);
        Assert.assertEquals(givenFoundPromotions(), filteredPromotions);
    }

    @Test
    public void retrieveByRestaurant() {
        BDDMockito.given(repository.findByRestaurantId(RESTAURANT_ID)).willReturn(givenPromotions());
        List<Promotion> promotionsByRestaurant = service.retrieveByRestaurant(RESTAURANT_ID);
        Assert.assertNotNull(promotionsByRestaurant);
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurantDTO = new FilteredRestaurantDTO();
        Promotion firstPromotion = new Promotion();
        firstPromotion.setName(COMBO_NAME);
        firstPromotion.setCategory(FoodCategory.BRAZILIAN_FOOD);
        Promotion secondPromotion = new Promotion();
        secondPromotion.setName(SECOND_COMBO_NAME);
        secondPromotion.setCategory(FoodCategory.CANDY_AND_CAKE);
        firstFilteredRestaurantDTO.setPromotions(Arrays.asList(firstPromotion, secondPromotion));
        FilteredRestaurantDTO secondFilteredRestaurantDTO = new FilteredRestaurantDTO();
        Promotion thirdPromotion = new Promotion();
        thirdPromotion.setName(THIRD_COMBO_NAME);
        thirdPromotion.setCategory(FoodCategory.CONVENIENCE);
        secondFilteredRestaurantDTO.setPromotions(Collections.singletonList(thirdPromotion));
        return Arrays.asList(firstFilteredRestaurantDTO, secondFilteredRestaurantDTO);
    }

    private List<Promotion> givenPromotions() {
        Promotion firstPromotion = new Promotion();
        firstPromotion.setName(COMBO_NAME);
        firstPromotion.setCategory(FoodCategory.BRAZILIAN_FOOD);
        Promotion secondPromotion = new Promotion();
        secondPromotion.setName(SECOND_COMBO_NAME);
        secondPromotion.setCategory(FoodCategory.CANDY_AND_CAKE);
        Promotion thirdPromotion = new Promotion();
        thirdPromotion.setName(THIRD_COMBO_NAME);
        thirdPromotion.setCategory(FoodCategory.CONVENIENCE);
        return Arrays.asList(firstPromotion, secondPromotion, thirdPromotion);
    }

    private List<Promotion> givenFoundPromotions() {
        Promotion promotion = new Promotion();
        promotion.setName(COMBO_NAME);
        promotion.setCategory(FoodCategory.BRAZILIAN_FOOD);
        return Collections.singletonList(promotion);
    }

}
