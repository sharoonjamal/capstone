package learn.aaron.closet.shop.domain;

import learn.aaron.closet.shop.data.CartRepository;
import learn.aaron.closet.shop.data.ImageRepository;
import learn.aaron.closet.shop.models.Cart;
import learn.aaron.closet.shop.models.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {


    private final ImageRepository repository;

    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    public List<Image> findAll() {
        return repository.findAll();
    }

    public Image findById(Long imageId) {
        return repository.findById(imageId);
    }

    public Result<Image> add(Image image) {
        Result<Image> result = validate(image);
        if (!result.isSuccess()) {
            return result;
        }

        if (image.getImageId() != null) {
            result.addMessage("imageId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        image = repository.add(image);
        result.setPayload(image);
        return result;
    }

    public Result<Image> update(Image image) {
        Result<Image> result = validate(image);
        if (!result.isSuccess()) {
            return result;
        }

        if (image.getImageId() <= 0) {
            result.addMessage("imageId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(image)) {
            String msg = String.format("imageId: %s, not found", image.getImageId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(Long imageId) {
        return repository.deleteById(imageId);
    }


    private Result<Image> validate(Image image) {
        // Implement validation logic
        // Check for null values, empty strings, etc.
        // Return a Result<Fan> object accordingly

        Result<Image> result = new Result<>();
        if (image == null) {
            result.addMessage("image cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isBlank(image.getProductId())) {
            result.addMessage("productId is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(image.getImageUrl())) {
            result.addMessage("imageUrl is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(image.getCaption())) {
            result.addMessage("caption is required", ResultType.INVALID);
        }

        return result;

    }
}