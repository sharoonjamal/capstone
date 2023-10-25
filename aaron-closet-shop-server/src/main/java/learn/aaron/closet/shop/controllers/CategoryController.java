package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.CategoryService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.models.Cart;
import learn.aaron.closet.shop.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
        private final CategoryService service;

        public CategoryController(CategoryService categoryService) {
            this.service = categoryService;
        }

        @GetMapping
        public List<Category> findAll() {
            return service.findAll();
        }

        @GetMapping("/{categoryId}")
        public Category findById(@PathVariable Long categoryId) {
            return service.findById(categoryId);
        }

        @PostMapping
        public ResponseEntity<Object> add(@RequestBody Category category) {
            Result<Category> result = service.add(category);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
            }
            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @PutMapping("/{categoryId}")
        public ResponseEntity<Object> update(@PathVariable long categoryId, @RequestBody Category category) {
            category.setCategoryId(categoryId);
            Result<Category> result = service.update(category);
            if (result.isSuccess()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @DeleteMapping("/{categoryId}")
        public ResponseEntity<Void> deleteById(@PathVariable Long categoryId) {
            if (service.deleteById(categoryId)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


