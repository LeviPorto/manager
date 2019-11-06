package com.levi.manager.controller;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.User;
import com.levi.manager.service.UserService;
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

import static com.levi.manager.util.UnitTestUtil.transformValuesInRequestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    private final Integer FIRST_USER_ID = 2;
    private final Integer SECOND_USER_ID = 3;

    private final String URL_BASE = "/manager/user";
    private final String URL_GET_BY_IDS = "/findByIds";

    @Test
    public void getUserByIds() throws Exception {
        BDDMockito.given(service.retrieveByIds(Arrays.asList(FIRST_USER_ID, SECOND_USER_ID))).willReturn(givenUsers());
        mvc.perform(MockMvcRequestBuilders.get(URL_BASE + URL_GET_BY_IDS)
                .param("ids", transformValuesInRequestParameters(FIRST_USER_ID, SECOND_USER_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    private List<User> givenUsers() {
        User firstUser = new User();
        firstUser.setId(FIRST_USER_ID);
        User secondUser = new User();
        secondUser.setId(SECOND_USER_ID);
        return Arrays.asList(firstUser, secondUser);
    }

}
