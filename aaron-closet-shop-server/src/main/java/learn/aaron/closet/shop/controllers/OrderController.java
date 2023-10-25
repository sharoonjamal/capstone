package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.OrderService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.models.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/orders")
public class OrderController {
        private final OrderService service;

        public OrderController(OrderService orderService) {
            this.service = orderService;
        }

        @GetMapping
        public List<Order> findAll() {
            return service.findAll();
        }

        @GetMapping("/{orderId}")
        public Order findById(@PathVariable Long orderId) {
            return service.findById(orderId);
        }

        @PostMapping
        public ResponseEntity<Object> add(@RequestBody Order order) {
            Result<Order> result = service.add(order);
            if (result.isSuccess()) {
                return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
            }
            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Order
        }

        @PutMapping("/{orderId}")
        public ResponseEntity<Object> update(@PathVariable long orderId, @RequestBody Order order) {
            order.setOrderId(orderId);
            Result<Order> result = service.update(order);
            if (result.isSuccess()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
        }

        @DeleteMapping("/{orderId}")
        public ResponseEntity<Void> deleteById(@PathVariable Long orderId) {
            if (service.deleteById(orderId)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

