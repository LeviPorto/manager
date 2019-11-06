package com.levi.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.dto.FilteredRestaurantDTO;
import com.levi.manager.dto.RestaurantSearchDTO;
import com.levi.manager.service.RestaurantService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService service;

    private ObjectMapper objectMapper;

    private final Integer RESTAURANT_ID = 1;
    private final Double RESTAURANT_LATITUDE = 20.0;
    private final Double RESTAURANT_LONGITUDE = 15.0;
    private final Integer SECOND_RESTAURANT_ID = 2;
    private final Double SECOND_RESTAURANT_LATITUDE = 15.0;
    private final Double SECOND_RESTAURANT_LONGITUDE = 15.0;

    private final Integer USER_ID = 1;
    private final String USER_CITY = "City 1";

    private final Integer DELIVERY_MAN_ID = 1;

    private final String URL_BASE = "/manager/restaurant";
    private final String URL_FILTERED_RESTAURANTS = "/search";
    private final String URL_FIND_BY_DELIVERY_MAN_ID = "/deliveryMan/{deliveryManId}";

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void search() throws Exception {
        BDDMockito.given(service.retrieveFilteredRestaurants(givenRestaurantSearchDTO())).willReturn(givenFilteredRestaurants());
        mvc.perform(MockMvcRequestBuilders.post(URL_BASE + URL_FILTERED_RESTAURANTS)
                .content(objectMapper.writeValueAsString(givenRestaurantSearchDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void findByDeliveryMan() throws Exception {
        BDDMockito.given(service.retrieveByDeliveryMan(DELIVERY_MAN_ID)).willReturn(givenRestaurant());
        mvc.perform(MockMvcRequestBuilders.get(URL_BASE + URL_FIND_BY_DELIVERY_MAN_ID, DELIVERY_MAN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    private RestaurantSearchDTO givenRestaurantSearchDTO() {
        RestaurantSearchDTO restaurantSearchDTO = new RestaurantSearchDTO();
        restaurantSearchDTO.setUserCity(USER_CITY);
        restaurantSearchDTO.setUserId(USER_ID);
        return restaurantSearchDTO;
    }

    private List<FilteredRestaurantDTO> givenFilteredRestaurants() {
        FilteredRestaurantDTO firstFilteredRestaurantDTO = new FilteredRestaurantDTO();
        firstFilteredRestaurantDTO.setRestaurantId(RESTAURANT_ID);
        firstFilteredRestaurantDTO.setLatitude(RESTAURANT_LATITUDE);
        firstFilteredRestaurantDTO.setLongitude(RESTAURANT_LONGITUDE);
        FilteredRestaurantDTO secondFilteredRestaurantDTO = new FilteredRestaurantDTO();
        secondFilteredRestaurantDTO.setRestaurantId(SECOND_RESTAURANT_ID);
        secondFilteredRestaurantDTO.setLatitude(SECOND_RESTAURANT_LATITUDE);
        secondFilteredRestaurantDTO.setLongitude(SECOND_RESTAURANT_LONGITUDE);
        return Arrays.asList(firstFilteredRestaurantDTO, secondFilteredRestaurantDTO);
    }

    private Restaurant givenRestaurant() {
        Restaurant restaurant = new Restaurant();
        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setId(DELIVERY_MAN_ID);
        restaurant.setDeliveryMan(Collections.singletonList(deliveryMan));
        return restaurant;
    }

}
