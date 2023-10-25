package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.CustomerService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.models.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

        private final CustomerService service;

        public CustomerController(CustomerService customerService) {
            this.service = customerService;
        }

        @GetMapping
        public List<Customer> findAll() {
            return service.findAll();
        }

        @GetMapping("/{customerId}")
        public Customer findById(@PathVariable Long customerId) {
            return service.findById(customerId);
        }

        @PostMapping
        public ResponseEntity<Object> add(@RequestBody Customer customer) {
            Result<Customer> result = service.add(customer);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
            }
            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @PutMapping("/{customerId}")
        public ResponseEntity<Object> update(@PathVariable long customerId, @RequestBody Customer customer) {
            customer.setCustomerId(customerId);
            Result<Customer> result = service.update(customer);
            if (result.isSuccess()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @DeleteMapping("/{customerId}")
        public ResponseEntity<Void> deleteById(@PathVariable Long customerId) {
            if (service.deleteById(customerId)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

