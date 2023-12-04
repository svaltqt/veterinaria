package com.app.veterinaria.controller;

import com.app.veterinaria.model.User;
import com.app.veterinaria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User newUser(@RequestBody User user){
        return userService.addUser(user);
    }
    @GetMapping
    public List<User> getUsers(){
        return userService.findAllUsers();
    }
    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId){
        return userService.getUserByUserId(userId);
    }
    @GetMapping("/email/{email}")
    public List<User> findUserUsingEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("/name/{name}")
    public List<User> findUserUsingName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    @GetMapping("/city/{city}")
    public List<User> findUserUsingCity(@PathVariable String city){
        return userService.getUserByCity(city);
    }
    @GetMapping("/age/{age}")
    public List<User> findUserUsingAge(@PathVariable int age){
        return userService.getUserByAge(age);
    }
    @PutMapping
    public User modifyUser(@RequestBody User user){
        return userService.updateUser(user);
    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return  userService.deleteUser(userId);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        User validatedUser = userService.validateUser(email, password);

        if (validatedUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(validatedUser);
    }


}
