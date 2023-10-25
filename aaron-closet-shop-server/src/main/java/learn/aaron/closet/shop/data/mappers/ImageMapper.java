package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Cart;
import learn.aaron.closet.shop.models.Image;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper implements RowMapper<Image> {
    public Image mapRow(ResultSet resultSet, int i) throws SQLException {
        Image image = new Image();
        image.setImageId(resultSet.getLong("image_id"));
        image.setProductId(resultSet.getLong("product_id"));
        image.setImageUrl(resultSet.getString("image_url"));
        image.setCaption(resultSet.getString("caption"));
        return image;
    }
}
