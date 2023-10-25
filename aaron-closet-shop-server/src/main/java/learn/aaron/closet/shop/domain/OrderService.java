package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.OrderRepository;
import learn.aaron.closet.shop.models.Order;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {
        private final OrderRepository repository;

        public OrderService(OrderRepository repository) {this.repository = repository;}

        public List<Order> findAll() {return repository.findAll();}

        public Order findById(Long orderId) {return repository.findById(orderId);}

        public Result<Order> add(Order order) {
            Result<Order> result = validate(order);
            if (!result.isSuccess()) {
                return result;
            }

            if (order.getOrderId() != null) {
                result.addMessage("orderId cannot be set for `add` operation", ResultType.INVALID);
                return result;
            }

            order = repository.add(order);
            result.setPayload(order);
            return result;
        }

        public Result<Order> update(Order order) {
            Result<Order> result = validate(order);
            if (!result.isSuccess()) {
                return result;
            }

            if (order.getOrderId() <= 0) {
                result.addMessage("orderId must be set for `update` operation", ResultType.INVALID);
                return result;
            }

            if (!repository.update(order)) {
                String msg = String.format("orderId: %s, not found", order.getOrderId());
                result.addMessage(msg, ResultType.NOT_FOUND);
            }

            return result;
        }

        public boolean deleteById(Long orderId) {
            return repository.deleteById(orderId);
        }



        private Result<Order> validate(Order order) {
            // Implement validation logic
            // Check for null values, empty strings, etc.
            // Return a Result<Fan> object accordingly

            Result<Order> result = new Result<>();
            if (order == null) {
                result.addMessage("order cannot be null", ResultType.INVALID);
                return result;
            }

            if (Validations.isBlank(order.getCustomerId())) {
                result.addMessage("customerId is required", ResultType.INVALID);
            }

            if (Validations.isNullDate(order.getOrderDate())) {
                result.addMessage("orderDate is required", ResultType.INVALID);
            }

            if (Validations.isNullOrBlank(order.getOrderStatus())) {
                result.addMessage("orderStatus is required", ResultType.INVALID);
            }

            if (Validations.isBlankDecimal(order.getTotalAmount())) {
                result.addMessage("totalAmount is required", ResultType.INVALID);
            }
            if (Validations.isNullOrBlank(order.getShippingMethod())) {
                result.addMessage("shippingMethod is required", ResultType.INVALID);
            }
            if (Validations.isNullOrBlank(order.getPaymentMethod())) {
                result.addMessage("paymentMethod is required", ResultType.INVALID);
            }

            return result;

        }
    }
