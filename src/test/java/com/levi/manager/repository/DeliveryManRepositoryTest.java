package com.levi.manager.repository;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.domain.User;
import com.levi.manager.domain.enumeration.Occupation;
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
public class DeliveryManRepositoryTest {

    @Autowired
    private DeliveryManRepository repository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

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

    private final Integer USER_ID = 1;
    private final String USER_COUNTRY = "First Country";
    private final String USER_CITY = "First City";
    private final String USER_EMAIL = "First Email";
    private final String USER_STATE = "First State";
    private final Double USER_LATITUDE = 12.0;
    private final Double USER_LONGITUDE = 14.0;
    private final String USER_PHONE = "923392339";
    private final String USER_NAME = "First Name";
    private final String USER_CPF = "First CPF";

    private final Integer FIRST_DELIVERY_MAN = 0;
    private final Integer SECOND_DELIVERY_MAN = 1;

    private final String DELIVERY_MAN_PHONE_IMEI = "4443322342142242";

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void findTop1ByRestaurantIdAndOccupation() {
        DeliveryMan deliveryMan = givenDeliveryMan();
        repository.save(deliveryMan);
        DeliveryMan deliveryManByRestaurantAndOccupation = repository.findTop1ByRestaurantIdAndOccupation(RESTAURANT_ID, Occupation.FREE);
        Assert.assertEquals(deliveryManByRestaurantAndOccupation.getOccupation(), Occupation.FREE);
    }

    @Test
    public void findByRestaurantIdAndOccupation() {
        DeliveryMan firstDeliveryMan = givenDeliveryMan();
        repository.save(firstDeliveryMan);
        DeliveryMan secondDeliveryMan = givenDeliveryMan();
        repository.save(secondDeliveryMan);
        List<DeliveryMan> deliveryMenByRestaurantAndOccupation = repository.findByRestaurantIdAndOccupation(RESTAURANT_ID, Occupation.FREE);
        Assert.assertEquals(deliveryMenByRestaurantAndOccupation.get(FIRST_DELIVERY_MAN).getOccupation(), Occupation.FREE);
        Assert.assertEquals(deliveryMenByRestaurantAndOccupation.get(SECOND_DELIVERY_MAN).getOccupation(), Occupation.FREE);
    }

    private DeliveryMan givenDeliveryMan() {
        DeliveryMan deliveryMan = new DeliveryMan();
        Restaurant restaurant = givenRestaurant();
        restaurantRepository.save(restaurant);
        deliveryMan.setOccupation(Occupation.FREE);
        deliveryMan.setRestaurant(restaurant);
        deliveryMan.setPhoneIMEI(DELIVERY_MAN_PHONE_IMEI);
        User user = givenUser();
        deliveryMan.setUser(user);
        userRepository.save(user);
        return deliveryMan;
    }

    private Restaurant givenRestaurant() {
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

    private User givenUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setCountry(USER_COUNTRY);
        user.setCity(USER_CITY);
        user.setEmail(USER_EMAIL);
        user.setState(USER_STATE);
        user.setLatitude(USER_LATITUDE);
        user.setLongitude(USER_LONGITUDE);
        user.setPhone(USER_PHONE);
        user.setName(USER_NAME);
        user.setCPF(USER_CPF);
        return user;
    }

}
