package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.CartRepository;
import learn.aaron.closet.shop.models.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository repository;

    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public List<Cart> findAll() {return repository.findAll();}

        public Cart findById(Long cartId) {return repository.findById(cartId);}

        public Result<Cart> add(Cart cart) {
            Result<Cart> result = validate(cart);
            if (!result.isSuccess()) {
                return result;
            }

            if (cart.getCartId() != null) {
                result.addMessage("cartId cannot be set for `add` operation", ResultType.INVALID);
                return result;
            }

            cart = repository.add(cart);
            result.setPayload(cart);
            return result;
        }

        public Result<Cart> update(Cart cart) {
            Result<Cart> result = validate(cart);
            if (!result.isSuccess()) {
                return result;
            }

            if (cart.getCartId() <= 0) {
                result.addMessage("cartId must be set for `update` operation", ResultType.INVALID);
                return result;
            }

            if (!repository.update(cart)) {
                String msg = String.format("cartId: %s, not found", cart.getCartId());
                result.addMessage(msg, ResultType.NOT_FOUND);
            }

            return result;
        }

        public boolean deleteById(Long cartId) {
            return repository.deleteById(cartId);
        }



        private Result<Cart> validate(Cart cart) {
            // Implement validation logic
            // Check for null values, empty strings, etc.
            // Return a Result<Fan> object accordingly

            Result<Cart> result = new Result<>();
            if (cart == null) {
                result.addMessage("cart cannot be null", ResultType.INVALID);
                return result;
            }

            if (Validations.isBlank(cart.getCustomerId())) {
                result.addMessage("customerId is required", ResultType.INVALID);
            }

            if (Validations.isBlank(cart.getProductId())) {
                result.addMessage("productId is required", ResultType.INVALID);
            }

            if (Validations.isBlankNumber(cart.getQuantity())) {
                result.addMessage("quantity is required", ResultType.INVALID);
            }

            if (Validations.isNullTimestamp(cart.getAddedToCart())) {
                result.addMessage("addedToCart is required", ResultType.INVALID);
            }

            return result;

        }
    }


