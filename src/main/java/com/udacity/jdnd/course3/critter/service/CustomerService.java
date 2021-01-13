package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @Transactional
    public Long save(Customer customer){
//
        return customerRepository.save(customer).getId();
    }

    public Customer getCustomerById(Long id){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.orElseGet(Customer::new);
    }

    public Customer getCustomerByPet(Long id){
        return customerRepository.findOwnerByPet(id);
    }


}
