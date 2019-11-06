package com.levi.manager.controller;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.Combo;
import com.levi.manager.domain.enumeration.FoodCategory;
import com.levi.manager.service.ComboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ComboControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ComboService service;

    private final String COMBO_NAME = "Combo 1";
    private final String SECOND_COMBO_NAME = "Combo 2";
    private final String THIRD_COMBO_NAME = "Combo 3";

    private final String USER_CITY = "Test City";
    private final String SEARCHED_NAME = "Combo 1";
    private final Integer USER_ID = 1;

    private final String URL_BASE = "/manager/combo/";
    private final String URL_FILTERED_COMBOS = "/search";
    private final String URL_FIND_BY_RESTAURANT = "/restaurant/{restaurantId}";

    private final String REQUEST_PARAM_SEARCHED_NAME = "searchedName";
    private final String REQUEST_PARAM_USER_CITY = "userCity";
    private final String REQUEST_PARAM_USER_ID = "userId";

    private final Integer RESTAURANT_ID = 1;

    @Test
    public void search() throws Exception {
        BDDMockito.given(service.retrieveFilteredCombos(SEARCHED_NAME, USER_CITY, USER_ID)).willReturn(givenCombos());
        mvc.perform(MockMvcRequestBuilders.get(URL_BASE + URL_FILTERED_COMBOS)
                .param(REQUEST_PARAM_SEARCHED_NAME, SEARCHED_NAME)
                .param(REQUEST_PARAM_USER_CITY, USER_CITY)
                .param(REQUEST_PARAM_USER_ID, USER_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void findByRestaurant() throws Exception {
        BDDMockito.given(service.retrieveByRestaurant(RESTAURANT_ID)).willReturn(givenCombos());
        mvc.perform(MockMvcRequestBuilders.get(URL_BASE + URL_FIND_BY_RESTAURANT, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
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

}
