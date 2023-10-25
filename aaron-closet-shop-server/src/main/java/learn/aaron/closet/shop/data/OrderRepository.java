package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    Order findById(Long order_id);

    Order add(Order order);

    boolean update(Order order);

    @Transactional
    boolean deleteById(Long id);
}
