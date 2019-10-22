package com.levi.manager.repository;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private final Integer FIRST_USER_ID = 1;
    private final Integer SECOND_USER_ID = 2;
    private final String USER_COUNTRY = "First Country";
    private final String USER_CITY = "First City";
    private final String USER_EMAIL = "First Email";
    private final String USER_STATE = "First State";
    private final Double USER_LATITUDE = 12.0;
    private final Double USER_LONGITUDE = 14.0;
    private final String USER_PHONE = "923392339";
    private final String USER_NAME = "First Name";
    private final String USER_CPF = "First CPF";

    private final Integer FIRST_USER = 0;
    private final Integer SECOND_USER = 1;

    @Test
    public void findByIdIn() {
        User firstUser = givenUser();
        repository.save(firstUser);
        User secondUser = givenUser();
        secondUser.setId(SECOND_USER_ID);
        repository.save(secondUser);
        List<User> users = repository.findByIdIn(Arrays.asList(FIRST_USER_ID, SECOND_USER_ID));
        Assert.assertEquals(users.get(FIRST_USER).getId(), FIRST_USER_ID);
        Assert.assertEquals(users.get(SECOND_USER).getId(), SECOND_USER_ID);
    }

    private User givenUser() {
        User user = new User();
        user.setId(FIRST_USER_ID);
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
