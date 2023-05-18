package com.wg.springboot3demo;

import com.wg.springboot3demo.entity.Customer;
import com.wg.springboot3demo.repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class ApplicationMain {

    private final CustomerRepository customerRepository;

    public ApplicationMain(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }

    @GetMapping("/api/v1/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/api/v1/customers")
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer(request.name(), request.email(), request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("/api/v1/customers/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("/api/v1/customers")
    public void updateCustomer(@RequestBody UpdateCustomerRequest request){
        Optional<Customer> customer = customerRepository.findById(request.id());
        if(customer.isPresent()){
            Customer updateCustomer = customer.get();
            updateCustomer.setName(request.name);
            updateCustomer.setEmail(request.email);
            updateCustomer.setAge(request.age);
            customerRepository.save(updateCustomer);
        } else {
            System.out.println("no customer found");
        }

    }

    record NewCustomerRequest(String name, String email, Integer age) {
    }

    record UpdateCustomerRequest(Integer id, String name, String email, Integer age){
    }
}
