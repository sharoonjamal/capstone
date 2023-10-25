package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Cart;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository {
    List<Cart> findAll();

    Cart findById(Long cart_id);

    Cart add(Cart cart);

    boolean update(Cart cart);

    @Transactional
    boolean deleteById(Long id);
}
