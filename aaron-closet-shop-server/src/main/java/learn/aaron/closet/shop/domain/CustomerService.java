package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.CustomerRepository;
import learn.aaron.closet.shop.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
        private final CustomerRepository repository;

        public CustomerService(CustomerRepository repository) {this.repository = repository;}

        public List<Customer> findAll() {return repository.findAll();}

        public Customer findById(Long orderId) {return repository.findById(orderId);}

        public Result<Customer> add(Customer customer) {
            Result<Customer> result = validate(customer);
            if (!result.isSuccess()) {
                return result;
            }

            if (customer.getCustomerId() != null) {
                result.addMessage("customerId cannot be set for `add` operation", ResultType.INVALID);
                return result;
            }

            customer = repository.add(customer);
            result.setPayload(customer);
            return result;
        }

        public Result<Customer> update(Customer customer) {
            Result<Customer> result = validate(customer);
            if (!result.isSuccess()) {
                return result;
            }

            if (customer.getCustomerId() <= 0) {
                result.addMessage("customerId must be set for `update` operation", ResultType.INVALID);
                return result;
            }

            if (!repository.update(customer)) {
                String msg = String.format("orderId: %s, not found", customer.getCustomerId());
                result.addMessage(msg, ResultType.NOT_FOUND);
            }

            return result;
        }

        public boolean deleteById(Long orderId) {
            return repository.deleteById(orderId);
        }



        private Result<Customer> validate(Customer customer) {
            // Implement validation logic
            // Check for null values, empty strings, etc.
            // Return a Result<Fan> object accordingly

            Result<Customer> result = new Result<>();
            if (customer == null) {
                result.addMessage("customer cannot be null", ResultType.INVALID);
                return result;
            }

            if (Validations.isNullOrBlank(customer.getFirstName())) {
                result.addMessage("firstName is required", ResultType.INVALID);
            }

            if (Validations.isNullOrBlank(customer.getLastName())) {
                result.addMessage("lastName is required", ResultType.INVALID);
            }

            if (Validations.isNullOrBlank(customer.getEmail())) {
                result.addMessage("email is required", ResultType.INVALID);
            }

            if (Validations.isNullOrBlank(customer.getPassword())) {
                result.addMessage("password is required", ResultType.INVALID);
            }
            if (Validations.isNullOrBlank(customer.getShippingAddress())) {
                result.addMessage("shippingAddress is required", ResultType.INVALID);
            }
            if (Validations.isNullOrBlank(customer.getBillingAddress())) {
                result.addMessage("billingAddress is required", ResultType.INVALID);
            }
            if (Validations.isNullOrBlank(customer.getPhoneNumber())) {
                result.addMessage("phoneNumber is required", ResultType.INVALID);
            }

            return result;

        }
    }


