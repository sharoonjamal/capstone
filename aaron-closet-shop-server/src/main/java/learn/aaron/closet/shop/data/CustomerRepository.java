package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();

    Customer findById(Long customer_id);

    Customer add(Customer customer);

    boolean update(Customer customer);

    @Transactional
    boolean deleteById(Long id);
}
