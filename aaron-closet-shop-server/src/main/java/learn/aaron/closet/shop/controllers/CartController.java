package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.CartService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.models.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/cart")
public class CartController {
        private final CartService service;

        public CartController(CartService cartService) {
            this.service = cartService;
        }

        @GetMapping
        public List<Cart> findAll() {
            return service.findAll();
        }

        @GetMapping("/{cartId}")
        public Cart findById(@PathVariable Long cartId) {
            return service.findById(cartId);
        }

        @PostMapping
        public ResponseEntity<Object> add(@RequestBody Cart cart) {
            Result<Cart> result = service.add(cart);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
            }
            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @PutMapping("/{cartId}")
        public ResponseEntity<Object> update(@PathVariable long cartId, @RequestBody Cart cart) {
            cart.setCartId(cartId);
            Result<Cart> result = service.update(cart);
            if (result.isSuccess()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @DeleteMapping("/{cartId}")
        public ResponseEntity<Void> deleteById(@PathVariable Long cartId) {
            if (service.deleteById(cartId)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


