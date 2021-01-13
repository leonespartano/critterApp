package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Long save(Employee employee){
        return employeeRepository.save(employee).getId();
    }

    public Employee getEmployeeById(Long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//        if(optionalEmployee.isEmpty()){
//            throw new CarNotFoundException();
//        }
        return optionalEmployee.get();
    }

    @Transactional
    public void setEmployeeAvailabilityById(Set<DayOfWeek> daysAvailable, long id){
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (!employeeOptional.isPresent()){

        }
        Employee employeeFounded = employeeOptional.get();
        employeeFounded.setDaysAvailable(daysAvailable);
        employeeRepository.save(employeeFounded);
    }

    public List<Employee> getEmployeesByServices(DayOfWeek desiredDate, Set<EmployeeSkill> skills){
        Iterable<Employee> employeeIt = employeeRepository.findAll();
        List<Employee> employeesForService = new ArrayList<>();
        for (Employee employee: employeeIt) {
            if (employee.getDaysAvailable().contains(desiredDate) && employee.getSkills().containsAll(skills)) {
                employeesForService.add(employee);
            }
        }

        return employeesForService;
    }
}
