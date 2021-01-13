package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.utils.DTOConversionUtils;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @Autowired
    DTOConversionUtils dtoConversionUtils;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = dtoConversionUtils.convertPetDTOtoEntity(petDTO);
        Customer owner = customerService.getCustomerById(petDTO.getOwnerId());
        pet.setOwner(owner);
        Long id = petService.save(pet);
        PetDTO newpetDTO = dtoConversionUtils.convertEntityToPetDTO(pet);
        newpetDTO.setId(id);
        return newpetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return dtoConversionUtils.convertEntityToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.getAllPets().stream().map(
                pet -> {
                    return dtoConversionUtils.convertEntityToPetDTO(pet);
                }
        ).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getAllPetsByOwner(ownerId);
        return pets.stream().map(
                pet -> dtoConversionUtils.convertEntityToPetDTO(pet)
        ).collect(Collectors.toList());
    }

    @PostMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId, @RequestBody PetDTO petDTO) {
        Pet pet = petService.getPetById(petId);
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setPetType(petDTO.getType());
        Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
        if(customer.getId() != null){
            pet.setOwner(customer);
        }
        petService.save(pet);


        petDTO.setId(petId);

        return petDTO;
    }
}
