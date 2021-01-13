package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.utils.DTOConversionUtils;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DTOConversionUtils dtoConversionUtils;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = dtoConversionUtils.convertCustomerDTOtoEntity(customerDTO);
        Long id = customerService.save(customer);
        customerDTO.setId(id);
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers().stream().map(
                customer -> {
                    return dtoConversionUtils.convertEntityToCustomerDTO(customer);
                }
        ).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPet(petId);
        if(customer != null){
            return dtoConversionUtils.convertEntityToCustomerDTO(customer);
        }
        return null;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = dtoConversionUtils.convertEmployeeDTOtoEntity(employeeDTO);
        Long id = employeeService.save(employee);
        employeeDTO.setId(id);
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return dtoConversionUtils.convertEntityToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailabilityById(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        LocalDate desiredDate = employeeRequestDTO.getDate();
        DayOfWeek desiredDay = desiredDate.getDayOfWeek();

        Set<EmployeeSkill> desiredSkills = employeeRequestDTO.getSkills();

        return employeeService.getEmployeesByServices(desiredDay, desiredSkills).stream()
                .map(
                        employee -> dtoConversionUtils.convertEntityToEmployeeDTO(employee)
                )
                .collect(Collectors.toList());

    }





}
