package com.app.veterinaria.service;

import com.app.veterinaria.model.Pet;
import com.app.veterinaria.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PetService {

    @Autowired
    private PetRepository repository;

    public List<Pet> getAllPets(){
        return repository.findAll();
    }
    public List<Pet> findPetByName(String name){
        return repository.findByName(name);
    }

    public List<Pet> findPetByBreed(String breed){
        return repository.findByBreed(breed);
    }

}
