package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.models.Product;
import learn.aaron.closet.shop.models.ReviewRating;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRatingRepository {

    List<ReviewRating> findAll();

    ReviewRating findById(Long review_id);

    ReviewRating add(ReviewRating reviewRating);

    boolean update(ReviewRating reviewRating);

    @Transactional
    boolean deleteById(Long id);
}
