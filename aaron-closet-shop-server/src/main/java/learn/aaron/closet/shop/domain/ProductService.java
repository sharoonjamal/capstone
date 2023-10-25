package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.ProductRepository;
import learn.aaron.closet.shop.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {this.repository = repository;}

    public List<Product> findAll() {return repository.findAll();}

    public Product findById(Long productId) {return repository.findById(productId);}

    public Result<Product> add(Product product) {
        Result<Product> result = validate(product);
        if (!result.isSuccess()) {
            return result;
        }

        if (product.getProductId() != null) {
            result.addMessage("productId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        product = repository.add(product);
        result.setPayload(product);
        return result;
    }

    public Result<Product> update(Product product) {
        Result<Product> result = validate(product);
        if (!result.isSuccess()) {
            return result;
        }

        if (product.getProductId() <= 0) {
            result.addMessage("characterId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(product)) {
            String msg = String.format("productId: %s, not found", product.getProductId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(Long productId) {
        return repository.deleteById(productId);
    }



    private Result<Product> validate(Product product) {
        // Implement validation logic
        // Check for null values, empty strings, etc.
        // Return a Result<Fan> object accordingly

        Result<Product> result = new Result<>();
        if (product == null) {
            result.addMessage("product cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(product.getName())) {
            result.addMessage("Name is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(product.getDescription())) {
            result.addMessage("description is required", ResultType.INVALID);
        }

        if (Validations.isBlankDecimal(product.getPrice())) {
            result.addMessage("price is required", ResultType.INVALID);
        }

        if (Validations.isBlankNumber(product.getStockQuantity())) {
            result.addMessage("stockQuantity  is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(product.getImageUrl())) {
            result.addMessage("imageUrl is required", ResultType.INVALID);
        }
        if (Validations.isNullOrBlank(product.getManufacturer())) {
            result.addMessage("manufacturer is required", ResultType.INVALID);
        }

        return result;

    }
}
