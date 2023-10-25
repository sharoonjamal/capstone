package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Product findById(Long product_id);

    Product add(Product product);

    boolean update(Product product);

    @Transactional
    boolean deleteById(Long id);
}
