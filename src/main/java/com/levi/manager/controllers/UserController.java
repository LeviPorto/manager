package com.levi.manager.controllers;

import com.levi.manager.entities.User;
import com.levi.manager.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/manager/user")
class UserController {

    private final UserService service;

    public UserController(final UserService service) {
        this.service = service;
    }

    //TODO verificar essas chamadas do Optional

    @GetMapping("/findById")
    public User getUserById(@RequestParam("id") Integer id) {
        return service.retrieveById(id).get();
    }

}
