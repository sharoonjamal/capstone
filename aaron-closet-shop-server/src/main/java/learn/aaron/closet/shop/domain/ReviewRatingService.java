package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.ProductRepository;
import learn.aaron.closet.shop.data.ReviewRatingRepository;
import learn.aaron.closet.shop.models.Product;
import learn.aaron.closet.shop.models.ReviewRating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewRatingService {
    private final ReviewRatingRepository repository;

    public ReviewRatingService(ReviewRatingRepository repository) {
        this.repository = repository;
    }

    public List<ReviewRating> findAll() {
        return repository.findAll();
    }

    public ReviewRating findById(Long reviewId) {
        return repository.findById(reviewId);
    }

    public Result<ReviewRating> add(ReviewRating reviewRating) {
        Result<ReviewRating> result = validate(reviewRating);
        if (!result.isSuccess()) {
            return result;
        }

        if (reviewRating.getReviewId() != null) {
            result.addMessage("reviewId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        reviewRating = repository.add(reviewRating);
        result.setPayload(reviewRating);
        return result;
    }

    public Result<ReviewRating> update(ReviewRating reviewRating) {
        Result<ReviewRating> result = validate(reviewRating);
        if (!result.isSuccess()) {
            return result;
        }

        if (reviewRating.getReviewId() <= 0) {
            result.addMessage("reviewId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(reviewRating)) {
            String msg = String.format("reviewId: %s, not found", reviewRating.getReviewId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(Long reviewId) {
        return repository.deleteById(reviewId);
    }


    private Result<ReviewRating> validate(ReviewRating reviewRating) {
        Result<ReviewRating> result = new Result<>();
        if (reviewRating == null) {
            result.addMessage("reviewRating cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isBlank(reviewRating.getReviewId())) {
            result.addMessage("Name is required", ResultType.INVALID);
        }

        if (Validations.isBlank(reviewRating.getProductId())) {
            result.addMessage("description is required", ResultType.INVALID);
        }

        if (Validations.isBlank(reviewRating.getCustomerId())) {
            result.addMessage("price is required", ResultType.INVALID);
        }

        if (Validations.isBlankNumber(reviewRating.getRating())) {
            result.addMessage("stockQuantity  is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(reviewRating.getReviewText())) {
            result.addMessage("imageUrl is required", ResultType.INVALID);
        }
        if (Validations.isNullTimestamp(reviewRating.getReviewDate())) {
            result.addMessage("manufacturer is required", ResultType.INVALID);
        }

        return result;

    }
}