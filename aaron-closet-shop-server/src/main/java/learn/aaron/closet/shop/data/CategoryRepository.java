package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    Category findById(Long category_id);

    Category add(Category category);

    boolean update(Category category);

    @Transactional
    boolean deleteById(Long id);
}
