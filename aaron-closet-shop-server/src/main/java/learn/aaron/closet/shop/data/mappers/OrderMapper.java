package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
    public class OrderMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            Order order = new Order();
            order.setOrderId(resultSet.getLong("order_id"));
            order.setCustomerId(resultSet.getLong("customer_id"));
            order.setOrderDate(resultSet.getDate("order_date"));
            order.setOrderStatus(resultSet.getString("order_status"));
            order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
            order.setShippingMethod(resultSet.getString("shipping_method"));
            order.setPaymentMethod(resultSet.getString("payment_method"));
            return order;
        }
    }
