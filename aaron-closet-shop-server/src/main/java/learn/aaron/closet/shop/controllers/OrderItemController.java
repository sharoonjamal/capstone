package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.CustomerService;
import learn.aaron.closet.shop.domain.OrderItemService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.models.Customer;
import learn.aaron.closet.shop.models.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {

    private final OrderItemService service;

    public OrderItemController(OrderItemService orderItemService) {
        this.service = orderItemService;
    }

    @GetMapping
    public List<OrderItem> findAll() {
        return service.findAll();
    }

    @GetMapping("/{orderItemId}")
    public OrderItem findById(@PathVariable Long orderItemId) {
        return service.findById(orderItemId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody OrderItem orderItem) {
        Result<OrderItem> result = service.add(orderItem);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<Object> update(@PathVariable long orderItemId, @RequestBody OrderItem orderItem) {
        orderItem.setOrderItemId(orderItemId);
        Result<OrderItem> result = service.update(orderItem);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long orderItemId) {
        if (service.deleteById(orderItemId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
