package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.ProductMapper;
import learn.aaron.closet.shop.data.mappers.ReviewRatingMapper;
import learn.aaron.closet.shop.models.Product;
import learn.aaron.closet.shop.models.ReviewRating;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReviewRatingJdbcTemplateRepository implements ReviewRatingRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReviewRatingJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReviewRating> findAll() {
        final String sql = "select review_id, product_id, customer_id, rating, review_text, review_date "
                + "from review_ratings limit 1000;";
        return jdbcTemplate.query(sql, new ReviewRatingMapper());
    }

    @Override
    @Transactional
    public ReviewRating findById(Long review_id) {

        final String sql = "select review_id, product_id, customer_id, rating, review_text, review_date  "
                + "from review_ratings "
                + "where review_id = ?;";

        ReviewRating reviewRating = jdbcTemplate.query(sql, new ReviewRatingMapper(), review_id).stream()
                .findFirst().orElse(null);

        return reviewRating;
    }

    @Override
    public ReviewRating add(ReviewRating reviewRating) {

        final String sql = "insert into review_ratings (product_id, customer_id, rating, review_text, review_date)"
                + " values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, reviewRating.getProductId());
            ps.setLong(2, reviewRating.getCustomerId());
            ps.setInt(3, reviewRating.getRating());
            ps.setString(4, reviewRating.getReviewText());
            ps.setTimestamp(5, reviewRating.getReviewDate());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        reviewRating.setProductId(keyHolder.getKey().longValue());
        return reviewRating;
    }

    @Override
    public boolean update(ReviewRating reviewRating) {

        final String sql = "update review_ratings set product_id = ?, customer_id = ?, rating = ?, review_text = ?, " +
                "review_date = ? where review_id = ?;";
        int res = jdbcTemplate.update(sql, reviewRating.getProductId(), reviewRating.getCustomerId(), reviewRating.getRating(),
                reviewRating.getReviewText(), reviewRating.getReviewDate(), reviewRating.getReviewId());
        if (res == 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean deleteById(Long reviewId) {
        return jdbcTemplate.update("delete from products where review_id = ?;", reviewId) > 0;
    }
}