package com.example.demo;

import com.example.demo.dao.UserDAO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserManager userManager = new UserManager(new UserDAO());

    @PostMapping("/login")
    User loginData(@RequestBody User loginData) {
        return userManager.login(loginData.getUsername(),loginData.getPassword());
    }
}
