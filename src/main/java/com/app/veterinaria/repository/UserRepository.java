package com.app.veterinaria.repository;

import com.app.veterinaria.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {


    List<User> findByName(String name);
    @Query("{city: ?0}")
    List<User> findByCity(String city);

    List<User> findByAge(int age);


    @Query("{email: ?0}")
    List<User> findByEmail(String email);
}
