package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.ProductService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {
        private final ProductService service;

        public ProductController(ProductService productService) {
            this.service = productService;
        }

        @GetMapping
        public List<Product> findAll() {
            return service.findAll();
        }

        @GetMapping("/{productId}")
        public Product findById(@PathVariable Long productId) {
            return service.findById(productId);
        }

        @PostMapping
        public ResponseEntity<Object> add(@RequestBody Product product) {
            Result<Product> result = service.add(product);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
            }
            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @PutMapping("/{productId}")
        public ResponseEntity<Object> update(@PathVariable long productId, @RequestBody Product product) {
            product.setProductId(productId);
            Result<Product> result = service.update(product);
            if (result.isSuccess()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @DeleteMapping("/{productId}")
        public ResponseEntity<Void> deleteById(@PathVariable Long productId) {
            if (service.deleteById(productId)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
