package learn.aaron.closet.shop.controllers;

import learn.aaron.closet.shop.domain.PaymentMethodService;
import learn.aaron.closet.shop.domain.Result;
import learn.aaron.closet.shop.models.PaymentMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paymentMethods")
public class PaymentMethodController {
    private final PaymentMethodService service;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.service = paymentMethodService;
    }

    @GetMapping
    public List<PaymentMethod> findAll() {
        return service.findAll();
    }

    @GetMapping("/{paymentMethodId}")
    public PaymentMethod findById(@PathVariable Long paymentMethodId) {
        return service.findById(paymentMethodId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody PaymentMethod paymentMethod) {
        Result<PaymentMethod> result = service.add(paymentMethod);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Order
    }

    @PutMapping("/{paymentMethodId}")
    public ResponseEntity<Object> update(@PathVariable long paymentMethodId, @RequestBody PaymentMethod paymentMethod) {
        paymentMethod.setPaymentMethodId(paymentMethodId);
        Result<PaymentMethod> result = service.update(paymentMethod);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);  // Ensure ErrorResponse is adapted for Character
    }

    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long paymentMethodId) {
        if (service.deleteById(paymentMethodId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}