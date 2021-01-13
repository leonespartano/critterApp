package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Long save(Pet pet){
        pet.setId(petRepository.save(pet).getId());
        Customer customer = pet.getOwner();
        if (customer != null){
            Optional<Customer> optionalCustomer = customerRepository.findById(pet.getOwner().getId());
            if (optionalCustomer.isPresent()) {
                customer = optionalCustomer.get();
                customer.addPet(pet);
                customerRepository.save(customer);
            }
        }

        return pet.getId();
    }

    public Pet getPetById(Long id){
        Optional<Pet> optionalPet = petRepository.findById(id);

        return optionalPet.orElseGet(Pet::new);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }
    public List<Pet> getAllPetsByOwner(Long id){
        return petRepository.findPetsByOwner(id);
    }
}
