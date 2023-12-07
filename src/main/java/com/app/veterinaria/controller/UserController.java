package com.app.veterinaria.controller;

import com.app.veterinaria.model.Pet;
import com.app.veterinaria.model.User;
import com.app.veterinaria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/names")
    public List<String> getAllUserNames() {
        return userService.getAllUserNames();
    }

    @GetMapping("/petnames")
    public List<String> getAllPetNames() {
        return userService.getAllPetNames();
    }

    @GetMapping("/getAllPetsInfo")
    public List<Pet> getAllPetsInfo() {
        return userService.getAllPetsInfo();
    }// http://localhost:8082/users/petsInfo

/*
    // Funciona con GET
    @GetMapping("/getAllPetsInfoByEmail")
    public ResponseEntity<?> getAllPetsInfoByEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo 'email' es obligatorio.");
        }
        List<Pet> pets = userService.getAllPetsInfoByEmail(email);
        if (pets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron mascotas para el usuario con el correo electrónico proporcionado.");
        }
        return ResponseEntity.ok(pets);    }

*/
    // FUnciona con Post
@PostMapping("/getAllPetsInfoByEmail")
public ResponseEntity<List<Pet>> getAllPetsInfoByEmail(@RequestBody Map<String, String> requestBody) {
    try {
        String email = requestBody.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // HttpStatus.BAD_REQUEST
        }

        List<Pet> pets = userService.getAllPetsInfoByEmail(email);

        if (pets.isEmpty()) {
            return ResponseEntity.notFound().build();  // HttpStatus.NOT_FOUND
        }

        return ResponseEntity.ok(pets);  // HttpStatus.OK
    } catch (Exception e) {
        // Manejar excepciones de manera adecuada
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // HttpStatus.INTERNAL_SERVER_ERROR
    }
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
