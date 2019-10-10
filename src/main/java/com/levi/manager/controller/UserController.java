package com.levi.manager.controller;

import com.levi.manager.crud.AbstractCrudController;
import com.levi.manager.domain.User;
import com.levi.manager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/manager/user")
class UserController extends AbstractCrudController<User> {

    private final UserService service;

    public UserController(final UserService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/findByIds")
    public List<User> getUserByIds(@RequestParam("ids") List<Integer> ids) {
        return service.retrieveByIds(ids);
    }

}
