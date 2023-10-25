package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.ReviewRatingRepository;
import learn.aaron.closet.shop.data.ShippingMethodRepository;
import learn.aaron.closet.shop.models.ReviewRating;
import learn.aaron.closet.shop.models.ShippingMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingMethodService {
    private final ShippingMethodRepository repository;

    public ShippingMethodService(ShippingMethodRepository repository) {
        this.repository = repository;
    }

    public List<ShippingMethod> findAll() {
        return repository.findAll();
    }

    public ShippingMethod findById(Long shippingMethodId) {
        return repository.findById(shippingMethodId);
    }

    public Result<ShippingMethod> add(ShippingMethod shippingMethod) {
        Result<ShippingMethod> result = validate(shippingMethod);
        if (!result.isSuccess()) {
            return result;
        }

        if (shippingMethod.getShippingMethodId() != null) {
            result.addMessage("shippingMethodId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        shippingMethod = repository.add(shippingMethod);
        result.setPayload(shippingMethod);
        return result;
    }

    public Result<ShippingMethod> update(ShippingMethod shippingMethod) {
        Result<ShippingMethod> result = validate(shippingMethod);
        if (!result.isSuccess()) {
            return result;
        }

        if (shippingMethod.getShippingMethodId() <= 0) {
            result.addMessage("shippingMethodId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(shippingMethod)) {
            String msg = String.format("shippingMethodId: %s, not found", shippingMethod.getShippingMethodId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(Long shippingMethodId) {
        return repository.deleteById(shippingMethodId);
    }


    private Result<ShippingMethod> validate(ShippingMethod shippingMethod) {
        Result<ShippingMethod> result = new Result<>();
        if (shippingMethod == null) {
            result.addMessage("shippingMethod cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(shippingMethod.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(shippingMethod.getDescription())) {
            result.addMessage("description is required", ResultType.INVALID);
        }

        if (Validations.isBlankDecimal(shippingMethod.getCost())) {
            result.addMessage("cost  is required", ResultType.INVALID);
        }

        return result;

    }
}