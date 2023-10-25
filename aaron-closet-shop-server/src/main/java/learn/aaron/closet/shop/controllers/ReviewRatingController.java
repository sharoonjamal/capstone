package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.ProductService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.domain.ReviewRatingService;
import learn.aaron.closet.shop.models.Product;
import learn.aaron.closet.shop.models.ReviewRating;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviewRatings")
public class ReviewRatingController {
    private final ReviewRatingService service;

    public ReviewRatingController(ReviewRatingService reviewRatingService) {
        this.service = reviewRatingService;
    }

    @GetMapping
    public List<ReviewRating> findAll() {return service.findAll();}

    @GetMapping("/{reviewId}")
    public ReviewRating findById(@PathVariable Long reviewId) {return service.findById(reviewId);}

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody ReviewRating reviewRating) {
        Result<ReviewRating> result = service.add(reviewRating);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Object> update(@PathVariable long reviewId, @RequestBody ReviewRating reviewRating) {
        reviewRating.setProductId(reviewId);
        Result<ReviewRating> result = service.update(reviewRating);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long reviewId) {
        if (service.deleteById(reviewId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}