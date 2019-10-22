package com.levi.manager.repository;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.Combo;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.domain.enumeration.FoodCategory;
import com.levi.manager.domain.enumeration.RestaurantCategory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class ComboRepositoryTest {

    @Autowired
    private ComboRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private final Integer RESTAURANT_ID = 1;
    private final String RESTAURANT_COUNTRY = "First Country";
    private final String RESTAURANT_CITY = "First City";
    private final String RESTAURANT_EMAIL = "First Email";
    private final RestaurantCategory RESTAURANT_CATEGORY = RestaurantCategory.ACAI;
    private final String RESTAURANT_STATE = "First State";
    private final Double RESTAURANT_LATITUDE = 12.0;
    private final Double RESTAURANT_LONGITUDE = 14.0;
    private final Double RESTAURANT_DELIVERY_FEE = 0.01;
    private final String RESTAURANT_PHONE = "923392339";
    private final Double RESTAURANT_COST = 3.2;
    private final String RESTAURANT_NAME = "First Name";
    private final String RESTAURANT_CNPJ = "First CNPJ";

    private final Integer SECOND_RESTAURANT_ID = 2;
    private final String SECOND_RESTAURANT_COUNTRY = "Second Country";
    private final String SECOND_RESTAURANT_CITY = "Second City";
    private final String SECOND_RESTAURANT_EMAIL = "Second Email";
    private final RestaurantCategory SECOND_RESTAURANT_CATEGORY = RestaurantCategory.BRAZILIAN_FOOD;
    private final String SECOND_RESTAURANT_STATE = "Second State";
    private final Double SECOND_RESTAURANT_LATITUDE = 11.0;
    private final Double SECOND_RESTAURANT_LONGITUDE = 11.0;
    private final Double SECOND_RESTAURANT_DELIVERY_FEE = 0.02;
    private final String SECOND_RESTAURANT_PHONE = "923392338";
    private final Double SECOND_RESTAURANT_COST = 4.1;
    private final String SECOND_RESTAURANT_NAME = "Second Name";
    private final String SECOND_RESTAURANT_CNPJ = "Second CNPJ";

    private final String COMBO_NAME = "First Combo Name";
    private final Double COMBO_PRICE = 3.0;
    private final FoodCategory COMBO_FOOD_CATEGORY = FoodCategory.CONVENIENCE;

    private final String SECOND_COMBO_NAME = "Second Combo Name";
    private final Double SECOND_COMBO_PRICE = 4.0;
    private final FoodCategory SECOND_COMBO_FOOD_CATEGORY = FoodCategory.CANDY_AND_CAKE;

    private final Integer COMBO = 0;

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void findByRestaurantId() {
        repository.save(givenFirstCombo());
        repository.save(givenSecondCombo());
        List<Combo> combosByRestaurant = repository.findByRestaurantId(RESTAURANT_ID);
        Assert.assertEquals(combosByRestaurant.get(COMBO).getId(), RESTAURANT_ID);
    }

    private Combo givenFirstCombo() {
        Combo combo = new Combo();
        Restaurant restaurant = givenFirstRestaurant();
        restaurantRepository.save(restaurant);
        combo.setRestaurant(restaurant);
        combo.setName(COMBO_NAME);
        combo.setPrice(COMBO_PRICE);
        combo.setCategory(COMBO_FOOD_CATEGORY);
        return combo;
    }

    private Combo givenSecondCombo() {
        Combo combo = new Combo();
        Restaurant restaurant = givenSecondRestaurant();
        restaurantRepository.save(restaurant);
        combo.setRestaurant(restaurant);
        combo.setName(SECOND_COMBO_NAME);
        combo.setPrice(SECOND_COMBO_PRICE);
        combo.setCategory(SECOND_COMBO_FOOD_CATEGORY);
        return combo;
    }

    private Restaurant givenFirstRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurant.setCountry(RESTAURANT_COUNTRY);
        restaurant.setCity(RESTAURANT_CITY);
        restaurant.setEmail(RESTAURANT_EMAIL);
        restaurant.setCategory(RESTAURANT_CATEGORY);
        restaurant.setEmail(RESTAURANT_EMAIL);
        restaurant.setLatitude(RESTAURANT_LATITUDE);
        restaurant.setLongitude(RESTAURANT_LONGITUDE);
        restaurant.setDeliveryFee(RESTAURANT_DELIVERY_FEE);
        restaurant.setPhone(RESTAURANT_PHONE);
        restaurant.setCost(RESTAURANT_COST);
        restaurant.setName(RESTAURANT_NAME);
        restaurant.setCNPJ(RESTAURANT_CNPJ);
        restaurant.setState(RESTAURANT_STATE);
        return restaurant;
    }

    private Restaurant givenSecondRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(SECOND_RESTAURANT_ID);
        restaurant.setCountry(SECOND_RESTAURANT_COUNTRY);
        restaurant.setCity(SECOND_RESTAURANT_CITY);
        restaurant.setEmail(SECOND_RESTAURANT_EMAIL);
        restaurant.setCategory(SECOND_RESTAURANT_CATEGORY);
        restaurant.setEmail(SECOND_RESTAURANT_EMAIL);
        restaurant.setLatitude(SECOND_RESTAURANT_LATITUDE);
        restaurant.setLongitude(SECOND_RESTAURANT_LONGITUDE);
        restaurant.setDeliveryFee(SECOND_RESTAURANT_DELIVERY_FEE);
        restaurant.setPhone(SECOND_RESTAURANT_PHONE);
        restaurant.setCost(SECOND_RESTAURANT_COST);
        restaurant.setName(SECOND_RESTAURANT_NAME);
        restaurant.setCNPJ(SECOND_RESTAURANT_CNPJ);
        restaurant.setState(SECOND_RESTAURANT_STATE);
        return restaurant;
    }

}
