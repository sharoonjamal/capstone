package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Order;
import learn.aaron.closet.shop.models.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(resultSet.getLong("order_item_id"));
        orderItem.setOrderId(resultSet.getLong("order_id"));
        orderItem.setProductId(resultSet.getLong("product_id"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setSubtotal(resultSet.getBigDecimal("subtotal"));
        return orderItem;
    }
}
