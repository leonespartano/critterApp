package com.udacity.jdnd.course3.critter.utils;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DTOConversionUtils {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    public  CustomerDTO convertEntityToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null) {
            List<Long> petIds = (customer.getPets().stream()
                    .map(
                            pet -> pet.getId()
                    )
                    .collect(Collectors.toList())
            );
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    public  Customer convertCustomerDTOtoEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public  EmployeeDTO convertEntityToEmployeeDTO(Employee employee){
        EmployeeDTO employeeTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeTO);
        return employeeTO;
    }

    public  Employee convertEmployeeDTOtoEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
    public PetDTO convertEntityToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setType(pet.getPetType());
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    public  Pet convertPetDTOtoEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        return pet;
    }

    public ScheduleDTO convertEntityToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Long> petIds = (schedule.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList())
        );
        List<Long> employeeIds = (schedule.getEmployees().stream()
                .map(Employee::getId)
                .collect(Collectors.toList())
        );
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }

    public Schedule convertScheduleDTOtoEntity(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Employee> employees = scheduleDTO.getEmployeeIds().stream().map(
                   id -> employeeRepository.findById(id).orElse(new Employee())
        )
                .collect(Collectors.toList());
        List<Pet> pets = scheduleDTO.getPetIds().stream().map(
                id-> petRepository.findById(id).orElse(new Pet())
        ).collect(Collectors.toList());
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return schedule;
    }


}
