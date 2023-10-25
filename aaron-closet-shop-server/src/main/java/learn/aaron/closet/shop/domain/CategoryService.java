package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.CartRepository;
import learn.aaron.closet.shop.data.CategoryRepository;
import learn.aaron.closet.shop.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long categoryId) {
        return repository.findById(categoryId);
    }

    public Result<Category> add(Category category) {
        Result<Category> result = validate(category);
        if (!result.isSuccess()) {
            return result;
        }

        if (category.getCategoryId() != null) {
            result.addMessage("categoryId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        category = repository.add(category);
        result.setPayload(category);
        return result;
    }

    public Result<Category> update(Category category) {
        Result<Category> result = validate(category);
        if (!result.isSuccess()) {
            return result;
        }

        if (category.getCategoryId() <= 0) {
            result.addMessage("categoryId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(category)) {
            String msg = String.format("categoryId: %s, not found", category.getCategoryId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(Long categoryId) {
        return repository.deleteById(categoryId);
    }


    private Result<Category> validate(Category category) {
        // Implement validation logic
        // Check for null values, empty strings, etc.
        // Return a Result<Fan> object accordingly

        Result<Category> result = new Result<>();
        if (category == null) {
            result.addMessage("category cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(category.getCategoryName())) {
            result.addMessage("category_name is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(category.getDescription())) {
            result.addMessage("description is required", ResultType.INVALID);
        }

        if (Validations.isBlank(category.getParentCategoryId())) {
            result.addMessage("parent_category_id is required", ResultType.INVALID);
        }

        return result;

    }
}