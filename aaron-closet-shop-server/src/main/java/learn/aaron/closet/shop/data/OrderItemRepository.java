package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Image;
import learn.aaron.closet.shop.models.Order;
import learn.aaron.closet.shop.models.OrderItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderItemRepository {


    List<OrderItem> findAll();

    OrderItem findById(Long order_item_id);

    OrderItem add(OrderItem orderItem);

    boolean update(OrderItem orderItem);

    @Transactional
    boolean deleteById(Long id);
}
