package com.app.veterinaria.service;

import com.app.veterinaria.model.User;
import com.app.veterinaria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;



@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User addUser(User user){
        user.setUserId(UUID.randomUUID().toString().split("-")[0]);
        return repository.save(user);
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User getUserByUserId(String userId){
        return repository.findById(userId).get();
    }
    public List<User> getUserByName(String name) {
        return repository.findByName(name);
    }
    public List<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<User> getUserByCity(String city) {
        return repository.findByCity(city);
    }

    public List<User> getUserByAge(int age) {
        return repository.findByAge(age);
    }


    public User validateUser(String email, String password) {

        List<User> users = repository.findByEmail(email);

        if(!users.isEmpty()) {
            User user = users.get(0);

            if(user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    // Update
    public User updateUser(User userRequest){
        User existingUser = repository.findById(userRequest.getUserId()).get();
        existingUser.setName(userRequest.getName());
        existingUser.setPassword(userRequest.getPassword());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setCity(userRequest.getCity());
        existingUser.setAge(userRequest.getAge());
        existingUser.setPet(userRequest.getPet());
        return repository.save(existingUser);
    }
   // Delete

   public String deleteUser(String userId){
        repository.deleteById(userId);
        return userId+" deleted";
   }

}
