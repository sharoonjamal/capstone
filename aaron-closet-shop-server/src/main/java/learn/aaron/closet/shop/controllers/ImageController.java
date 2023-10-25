package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.CartService;
import learn.aaron.closet.shop.domain.ImageService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.models.Cart;
import learn.aaron.closet.shop.models.Image;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/images")
public class ImageController {
        private final ImageService service;

        public ImageController(ImageService imageService) {
            this.service = imageService;
        }

        @GetMapping
        public List<Image> findAll() {
            return service.findAll();
        }

        @GetMapping("/{imageId}")
        public Image findById(@PathVariable Long imageId) {
            return service.findById(imageId);
        }

        @PostMapping
        public ResponseEntity<Object> add(@RequestBody Image image) {
            Result<Image> result = service.add(image);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
            }
            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @PutMapping("/{imageId}")
        public ResponseEntity<Object> update(@PathVariable long imageId, @RequestBody Image image) {
            image.setImageId(imageId);
            Result<Image> result = service.update(image);
            if (result.isSuccess()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @DeleteMapping("/{imageId}")
        public ResponseEntity<Void> deleteById(@PathVariable Long imageId) {
            if (service.deleteById(imageId)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}
