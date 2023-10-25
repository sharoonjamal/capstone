package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.domain.ShippingMethodService;
import learn.aaron.closet.shop.models.ShippingMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shippingMethods")
public class ShippingMethodController {
    private final ShippingMethodService service;

    public ShippingMethodController(ShippingMethodService shippingMethodService) {this.service = shippingMethodService;}

    @GetMapping
    public List<ShippingMethod> findAll() {return service.findAll();}

    @GetMapping("/{shippingMethodId}")
    public ShippingMethod findById(@PathVariable Long shippingMethodId) {return service.findById(shippingMethodId);}

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody ShippingMethod shippingMethod) {
        Result<ShippingMethod> result = service.add(shippingMethod);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
    }

    @PutMapping("/{shippingMethodId}")
    public ResponseEntity<Object> update(@PathVariable long shippingMethodId, @RequestBody ShippingMethod shippingMethod) {
        shippingMethod.setShippingMethodId(shippingMethodId);
        Result<ShippingMethod> result = service.update(shippingMethod);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{shippingMethodId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long shippingMethodId) {
        if (service.deleteById(shippingMethodId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}