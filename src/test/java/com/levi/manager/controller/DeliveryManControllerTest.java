package com.levi.manager.controller;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.domain.Restaurant;
import com.levi.manager.domain.enumeration.Occupation;
import com.levi.manager.service.DeliveryManService;
import com.levi.manager.service.RestaurantService;
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
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class DeliveryManControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeliveryManService service;

    private final String URL_BASE = "/manager/deliveryMan";
    private final String URL_FIND_BY_WAITING_ACCEPTANCE = "/restaurant/{restaurantId}/waitingAcceptance";
    private final String URL_ACCEPT_JOB = "/{id}/acceptJob";

    private final Integer RESTAURANT_ID = 1;
    private final Integer DELIVERY_MAN_ID = 1;

    @Test
    public void findByRestaurantAndWaitingAcceptance() throws Exception {
        BDDMockito.given(service.retrieveByRestaurantAndWaitingAcceptance(RESTAURANT_ID)).willReturn(givenDeliveryMen());
        mvc.perform(MockMvcRequestBuilders.get(URL_BASE + URL_FIND_BY_WAITING_ACCEPTANCE, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void acceptJob() throws Exception {
        BDDMockito.given(service.acceptJob(DELIVERY_MAN_ID)).willReturn(givenDeliveryMan());
        mvc.perform(MockMvcRequestBuilders.put(URL_BASE + URL_ACCEPT_JOB, DELIVERY_MAN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    private List<DeliveryMan> givenDeliveryMen() {
        DeliveryMan firstDeliveryMan = new DeliveryMan();
        firstDeliveryMan.setOccupation(Occupation.FREE);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        firstDeliveryMan.setRestaurant(restaurant);
        DeliveryMan secondDeliveryMan = new DeliveryMan();
        secondDeliveryMan.setOccupation(Occupation.FREE);
        secondDeliveryMan.setRestaurant(restaurant);
        return Arrays.asList(firstDeliveryMan, secondDeliveryMan);
    }

    private DeliveryMan givenDeliveryMan() {
        DeliveryMan deliveryMan = new DeliveryMan();
        deliveryMan.setOccupation(Occupation.FREE);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        deliveryMan.setRestaurant(restaurant);
        return deliveryMan;
    }

}
