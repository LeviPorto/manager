package com.levi.manager.service;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.Combo;
import com.levi.manager.domain.enumeration.FoodCategory;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.repository.ComboRepository;
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
public class ComboServiceTest {

    @InjectMocks
    private ComboService service;

    @Mock
    private ComboRepository repository;

    @Mock
    private RestaurantService restaurantService;

    private final String USER_CITY = "Test City";
    private final String SEARCHED_NAME = "Combo 1";
    private final Integer USER_ID = 1;

    private final String COMBO_NAME = "Combo 1";
    private final String SECOND_COMBO_NAME = "Combo 2";
    private final String THIRD_COMBO_NAME = "Combo 3";

    private final Integer RESTAURANT_ID = 1;

    @Test
    public void retrieveFilteredCombosWithoutSearchedName() {
        BDDMockito.given(restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(USER_CITY).userId(USER_ID).build())).willReturn(givenFilteredRestaurants());
        List<Combo> filteredCombos = service.retrieveFilteredCombos(null , USER_CITY, USER_ID);
        Assert.assertEquals(givenCombos(), filteredCombos);
    }

    @Test
    public void retrieveFilteredCombosWithSearchedName() {
        BDDMockito.given(restaurantService.retrieveFilteredRestaurants(RestaurantSearchDTO.builder().userCity(USER_CITY).userId(USER_ID).build())).willReturn(givenFilteredRestaurants());
        List<Combo> filteredCombos = service.retrieveFilteredCombos(SEARCHED_NAME , USER_CITY, USER_ID);
        Assert.assertEquals(givenFoundCombos(), filteredCombos);
    }

    @Test
    public void retrieveByRestaurant() {
        BDDMockito.given(repository.findByRestaurantId(RESTAURANT_ID)).willReturn(givenCombos());
        List<Combo> combosByRestaurant = service.retrieveByRestaurant(RESTAURANT_ID);
        Assert.assertNotNull(combosByRestaurant);
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurantDTO = new FilteredRestaurantDTO();
        Combo firstCombo = new Combo();
        firstCombo.setName(COMBO_NAME);
        firstCombo.setCategory(FoodCategory.BRAZILIAN_FOOD);
        Combo secondCombo = new Combo();
        secondCombo.setName(SECOND_COMBO_NAME);
        secondCombo.setCategory(FoodCategory.CANDY_AND_CAKE);
        firstFilteredRestaurantDTO.setCombos(Arrays.asList(firstCombo, secondCombo));
        FilteredRestaurantDTO secondFilteredRestaurantDTO = new FilteredRestaurantDTO();
        Combo thirdCombo = new Combo();
        thirdCombo.setName(THIRD_COMBO_NAME);
        thirdCombo.setCategory(FoodCategory.CONVENIENCE);
        secondFilteredRestaurantDTO.setCombos(Collections.singletonList(thirdCombo));
        return Arrays.asList(firstFilteredRestaurantDTO, secondFilteredRestaurantDTO);
    }

    private List<Combo> givenCombos() {
        Combo firstCombo = new Combo();
        firstCombo.setName(COMBO_NAME);
        firstCombo.setCategory(FoodCategory.BRAZILIAN_FOOD);
        Combo secondCombo = new Combo();
        secondCombo.setName(SECOND_COMBO_NAME);
        secondCombo.setCategory(FoodCategory.CANDY_AND_CAKE);
        Combo thirdCombo = new Combo();
        thirdCombo.setName(THIRD_COMBO_NAME);
        thirdCombo.setCategory(FoodCategory.CONVENIENCE);
        return Arrays.asList(firstCombo, secondCombo, thirdCombo);
    }

    private List<Combo> givenFoundCombos() {
        Combo combo = new Combo();
        combo.setName(COMBO_NAME);
        combo.setCategory(FoodCategory.BRAZILIAN_FOOD);
        return Collections.singletonList(combo);
    }

}
