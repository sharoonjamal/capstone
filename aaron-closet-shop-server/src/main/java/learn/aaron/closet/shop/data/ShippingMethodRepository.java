package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.ReviewRating;
import learn.aaron.closet.shop.models.ShippingMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShippingMethodRepository {

    List<ShippingMethod> findAll();

    ShippingMethod findById(Long shipping_method_id);

    ShippingMethod add(ShippingMethod shippingMethod);

    boolean update(ShippingMethod shippingMethod);

    @Transactional
    boolean deleteById(Long id);
}
