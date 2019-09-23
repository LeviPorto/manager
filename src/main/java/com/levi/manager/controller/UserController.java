package com.levi.manager.controller;

import com.levi.manager.entity.User;
import com.levi.manager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/manager/user")
class UserController {

    private final UserService service;

    public UserController(final UserService service) {
        this.service = service;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.create(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Integer id) {
        return service.update(user, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.remove(id);
    }

    //TODO verificar essas chamadas do Optional

    @GetMapping("/findByIds")
    public List<User> getUserByIds(@RequestParam("ids") List<Integer> ids) {
        return service.retrieveByIds(ids);
    }

}
