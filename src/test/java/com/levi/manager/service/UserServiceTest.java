package com.levi.manager.service;

import com.levi.manager.ManagerApplication;
import com.levi.manager.domain.User;
import com.levi.manager.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private final Integer FIRST_USER_ID = 2;
    private final Integer SECOND_USER_ID = 3;

    @Test
    public void retrieveByIds() {
        BDDMockito.given(repository.findByIdIn(Arrays.asList(FIRST_USER_ID, SECOND_USER_ID))).willReturn(givenUsers());
        List<User> users = service.retrieveByIds(Arrays.asList(FIRST_USER_ID, SECOND_USER_ID));
        assertNotNull(users);
    }

    private List<User> givenUsers() {
        User firstUser = new User();
        firstUser.setId(FIRST_USER_ID);
        User secondUser = new User();
        secondUser.setId(SECOND_USER_ID);
        return Arrays.asList(firstUser, secondUser);
    }

}
