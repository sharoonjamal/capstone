package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Cart;
import learn.aaron.closet.shop.models.Image;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepository {

    List<Image> findAll();

    Image findById(Long image_id);

    Image add(Image image);

    boolean update(Image image);

    @Transactional
    boolean deleteById(Long id);
}
