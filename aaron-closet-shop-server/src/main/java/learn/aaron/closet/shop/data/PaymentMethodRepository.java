package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Order;
import learn.aaron.closet.shop.models.PaymentMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaymentMethodRepository {

    List<PaymentMethod> findAll();

    PaymentMethod findById(Long payment_method_id);

    PaymentMethod add(PaymentMethod paymentMethod);

    boolean update(PaymentMethod paymentMethod);

    @Transactional
    boolean deleteById(Long id);
}
