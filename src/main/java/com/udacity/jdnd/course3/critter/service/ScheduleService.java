package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Transactional
    public Long save(Schedule schedule){
        return scheduleRepository.save(schedule).getId();
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getAllSchedulesByEmployee(long id){
        return scheduleRepository.findAllSchedulesByEmployee(id);
    }

    public List<Schedule> getAllSchedulesByPet(long id){
        return scheduleRepository.findAllSchedulesByPet(id);
    }

    public List<Schedule> getAllSchedulesByCustomer(long id){
        List<Pet> pets = petRepository.findPetsByOwner(id);
        Set<Schedule> schedules = new HashSet<>();
        pets.forEach(
                pet -> schedules.addAll(scheduleRepository.findAllSchedulesByPet(pet.getId()))
        );
        return new ArrayList<>(schedules);
    }

}
