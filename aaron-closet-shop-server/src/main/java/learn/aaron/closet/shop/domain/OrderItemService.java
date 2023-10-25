package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.CustomerRepository;
import learn.aaron.closet.shop.data.OrderItemRepository;
import learn.aaron.closet.shop.models.Customer;
import learn.aaron.closet.shop.models.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    public List<OrderItem> findAll() {
        return repository.findAll();
    }

    public OrderItem findById(Long orderItemId) {
        return repository.findById(orderItemId);
    }

    public Result<OrderItem> add(OrderItem orderItem) {
        Result<OrderItem> result = validate(orderItem);
        if (!result.isSuccess()) {
            return result;
        }

        if (orderItem.getOrderItemId() != null) {
            result.addMessage("orderItemId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        orderItem = repository.add(orderItem);
        result.setPayload(orderItem);
        return result;
    }

    public Result<OrderItem> update(OrderItem orderItem) {
        Result<OrderItem> result = validate(orderItem);
        if (!result.isSuccess()) {
            return result;
        }

        if (orderItem.getOrderItemId() <= 0) {
            result.addMessage("orderItemId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(orderItem)) {
            String msg = String.format("orderItemId: %s, not found", orderItem.getOrderItemId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(Long orderItemId) {
        return repository.deleteById(orderItemId);
    }


    private Result<OrderItem> validate(OrderItem orderItem) {
        // Implement validation logic
        // Check for null values, empty strings, etc.
        // Return a Result<Fan> object accordingly

        Result<OrderItem> result = new Result<>();
        if (orderItem == null) {
            result.addMessage("orderItems cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isBlank(orderItem.getOrderId())) {
            result.addMessage("orderId is required", ResultType.INVALID);
        }

        if (Validations.isBlank(orderItem.getProductId())) {
            result.addMessage("productId is required", ResultType.INVALID);
        }

        if (Validations.isBlankNumber(orderItem.getQuantity())) {
            result.addMessage("email is required", ResultType.INVALID);
        }

        if (Validations.isBlankDecimal(orderItem.getSubtotal())) {
            result.addMessage("password is required", ResultType.INVALID);
        }

        return result;

    }
}