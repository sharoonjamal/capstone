package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Product;
import learn.aaron.closet.shop.models.ReviewRating;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRatingMapper implements RowMapper<ReviewRating> {
    @Override
    public ReviewRating mapRow(ResultSet resultSet, int i) throws SQLException {
        ReviewRating reviewRating = new ReviewRating();
        reviewRating.setReviewId(resultSet.getLong("review_id"));
        reviewRating.setProductId(resultSet.getLong("product_id"));
        reviewRating.setCustomerId(resultSet.getLong("customer_id"));
        reviewRating.setRating(resultSet.getInt("rating"));
        reviewRating.setReviewText(resultSet.getString("review_text"));
        reviewRating.setReviewDate(resultSet.getTimestamp("review_date"));
        return reviewRating;
    }
}

