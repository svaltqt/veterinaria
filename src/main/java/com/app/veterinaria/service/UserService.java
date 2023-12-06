package com.app.veterinaria.service;

import com.app.veterinaria.model.Pet;
import com.app.veterinaria.model.User;
import com.app.veterinaria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;



@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

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


    // Buscar los usuarios solo por nombre y enlistarlos
    public List<String> getAllUserNames() {
        List<User> users = repository.findAll();
        List<String> userNames = new ArrayList<>();

        for (User user : users) {
            userNames.add(user.getName());
        }

        return userNames;
    }

    // Busca en los usuarios todas las pets y crea una lista con los nombres
    public List<String> getAllPetNames() {
        List<User> users = repository.findAll();
        List<String> petNames = new ArrayList<>();

        for (User user : users) {
            List<Pet> pets = user.getPet();
            if (pets != null && !pets.isEmpty()) {
                for (Pet pet : pets) {
                    petNames.add(pet.getName());
                }
            }
        }

        return petNames;
    }

    // Trae todas las pets en el documento y las enlista
    public List<Pet> getAllPetsInfo() {
        List<User> users = repository.findAll();
        List<Pet> getAllPetsInfo = new ArrayList<>();

        for (User user : users) {
            List<Pet> pets = user.getPet();
            if (pets != null && !pets.isEmpty()) {
                for (Pet pet : pets) {
                    // Crea una nueva instancia de Pet sin el historial médico
                    Pet petInfo = new Pet();
                    petInfo.setName(pet.getName());
                    petInfo.setType(pet.getType());
                    petInfo.setBreed(pet.getBreed());
                    petInfo.setAge(pet.getAge());
                    getAllPetsInfo.add(petInfo);
                }
            }
        }

        return getAllPetsInfo;
    }

    public List<Pet> getAllPetsInfoByEmail(String userEmail) {
        List<User> users = repository.findByEmail(userEmail);
        List<Pet> getAllPetsInfo = new ArrayList<>();

        for (User user : users) {
            List<Pet> pets = user.getPet();
            if (pets != null && !pets.isEmpty()) {
                for (Pet pet : pets) {
                    // Crea una nueva instancia de Pet sin el historial médico
                    Pet petInfo = new Pet();
                    petInfo.setName(pet.getName());
                    petInfo.setType(pet.getType());
                    petInfo.setBreed(pet.getBreed());
                    petInfo.setAge(pet.getAge());
                    getAllPetsInfo.add(petInfo);
                }
            }
        }

        return getAllPetsInfo;
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
