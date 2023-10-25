package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.OrderRepository;
import learn.aaron.closet.shop.data.PaymentMethodRepository;
import learn.aaron.closet.shop.models.Order;
import learn.aaron.closet.shop.models.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository repository;

    public PaymentMethodService(PaymentMethodRepository repository) {
        this.repository = repository;
    }

    public List<PaymentMethod> findAll() {
        return repository.findAll();
    }

    public PaymentMethod findById(Long paymentMethodId) {
        return repository.findById(paymentMethodId);
    }

    public Result<PaymentMethod> add(PaymentMethod paymentMethod) {
        Result<PaymentMethod> result = validate(paymentMethod);
        if (!result.isSuccess()) {
            return result;
        }

        if (paymentMethod.getPaymentMethodId() != null) {
            result.addMessage("paymentMethodId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        paymentMethod = repository.add(paymentMethod);
        result.setPayload(paymentMethod);
        return result;
    }

    public Result<PaymentMethod> update(PaymentMethod paymentMethod) {
        Result<PaymentMethod> result = validate(paymentMethod);
        if (!result.isSuccess()) {
            return result;
        }

        if (paymentMethod.getPaymentMethodId() <= 0) {
            result.addMessage("paymentMethodId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(paymentMethod)) {
            String msg = String.format("paymentMethodId: %s, not found", paymentMethod.getPaymentMethodId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(Long paymentMethodId) {
        return repository.deleteById(paymentMethodId);
    }


    private Result<PaymentMethod> validate(PaymentMethod paymentMethod) {

        Result<PaymentMethod> result = new Result<>();
        if (paymentMethod == null) {
            result.addMessage("paymentMethod cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(paymentMethod.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(paymentMethod.getDescription())) {
            result.addMessage("description is required", ResultType.INVALID);
        }

        return result;

    }
}

