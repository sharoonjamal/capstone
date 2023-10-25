package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            Product product = new Product();
            product.setProductId(resultSet.getLong("product_id"));
            product.setName(resultSet.getString("name"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getBigDecimal("price"));
            product.setStockQuantity(resultSet.getInt("stock_quantity"));
            product.setImageUrl(resultSet.getString("image_url"));
            product.setManufacturer(resultSet.getString("manufacturer"));
            return product;
        }
    }
