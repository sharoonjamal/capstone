package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartMapper implements RowMapper<Cart> {
    public Cart mapRow(ResultSet resultSet, int i) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(resultSet.getLong("cart_id"));
        cart.setCustomerId(resultSet.getLong("customer_id"));
        cart.setProductId(resultSet.getLong("product_id"));
        cart.setQuantity(resultSet.getInt("quantity"));
        cart.setAddedToCart(resultSet.getTimestamp("added_to_cart"));
        return cart;
    }
}
