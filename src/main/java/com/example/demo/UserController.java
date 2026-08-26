package com.example.demo;

import com.example.demo.dao.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserManager userManager = new UserManager(new UserDAO());

    @PostMapping("/login")
    public ResponseEntity<User> loginData(@RequestBody User loginData) {
        User loggedInUser = userManager.login(loginData.getUsername(),loginData.getPassword());
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else  {
            return ResponseEntity.ok(loggedInUser);
        }
    }

    @PostMapping("/register")
    public void registerData(@RequestBody User registerData) {
        userManager.addUser(registerData.getUsername(), registerData.getEmail(), registerData.getPassword());
    }
}
