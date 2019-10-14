package com.levi.manager.service;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.Food;
import com.levi.manager.domain.enumeration.FoodCategory;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.repository.FoodRepository;
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
public class FoodServiceTest {

    @InjectMocks
    private FoodService service;

    @Mock
    private FoodRepository repository;

    @Mock
    private RestaurantService restaurantService;

    private final String USER_CITY = "Test City";
    private final String SEARCHED_NAME = "Food 1";
    private final Integer USER_ID = 1;

    private final String FIRST_COMBO_NAME = "Food 1";
    private final String SECOND_COMBO_NAME = "Food 2";
    private final String THIRD_COMBO_NAME = "Food 3";

    private final Integer RESTAURANT_ID = 1;

    @Test
    public void retrieveFilteredFoodsWithoutSearchedName() {
        BDDMockito.given(restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(USER_CITY).userId(USER_ID).build())).willReturn(givenFilteredRestaurants());
        List<Food> filteredFoods = service.retrieveFilteredFoods(null , USER_CITY, USER_ID);
        Assert.assertEquals(givenFoods(), filteredFoods);
    }

    @Test
    public void retrieveFilteredFoodsWithSearchedName() {
        BDDMockito.given(restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(USER_CITY).userId(USER_ID).build())).willReturn(givenFilteredRestaurants());
        List<Food> filteredFoods = service.retrieveFilteredFoods(SEARCHED_NAME , USER_CITY, USER_ID);
        Assert.assertEquals(givenFoundFoods(), filteredFoods);
    }

    @Test
    public void retrieveByRestaurant() {
        BDDMockito.given(repository.findByRestaurantId(RESTAURANT_ID)).willReturn(givenFoods());
        List<Food> foodsByRestaurant = service.retrieveByRestaurant(RESTAURANT_ID);
        Assert.assertNotNull(foodsByRestaurant);
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurantDTO = new FilteredRestaurantDTO();
        Food firstFood = new Food();
        firstFood.setName(FIRST_COMBO_NAME);
        firstFood.setCategory(FoodCategory.BRAZILIAN_FOOD);
        Food secondFood = new Food();
        secondFood.setName(SECOND_COMBO_NAME);
        secondFood.setCategory(FoodCategory.CANDY_AND_CAKE);
        firstFilteredRestaurantDTO.setFoods(Arrays.asList(firstFood, secondFood));
        FilteredRestaurantDTO secondFilteredRestaurantDTO = new FilteredRestaurantDTO();
        Food thirdFood = new Food();
        thirdFood.setName(THIRD_COMBO_NAME);
        thirdFood.setCategory(FoodCategory.CONVENIENCE);
        secondFilteredRestaurantDTO.setFoods(Collections.singletonList(thirdFood));
        return Arrays.asList(firstFilteredRestaurantDTO, secondFilteredRestaurantDTO);
    }

    private List<Food> givenFoods() {
        Food firstFood = new Food();
        firstFood.setName(FIRST_COMBO_NAME);
        firstFood.setCategory(FoodCategory.BRAZILIAN_FOOD);
        Food secondFood = new Food();
        secondFood.setName(SECOND_COMBO_NAME);
        secondFood.setCategory(FoodCategory.CANDY_AND_CAKE);
        Food thirdFood = new Food();
        thirdFood.setName(THIRD_COMBO_NAME);
        thirdFood.setCategory(FoodCategory.CONVENIENCE);
        return Arrays.asList(firstFood, secondFood, thirdFood);
    }

    private List<Food> givenFoundFoods() {
        Food food = new Food();
        food.setName(FIRST_COMBO_NAME);
        food.setCategory(FoodCategory.BRAZILIAN_FOOD);
        return Collections.singletonList(food);
    }

}
