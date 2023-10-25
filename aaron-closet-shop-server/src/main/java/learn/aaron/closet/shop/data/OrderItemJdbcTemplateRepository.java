package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.CartMapper;
import learn.aaron.closet.shop.data.mappers.OrderItemMapper;
import learn.aaron.closet.shop.models.Cart;
import learn.aaron.closet.shop.models.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class OrderItemJdbcTemplateRepository implements OrderItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderItemJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderItem> findAll() {
        final String sql = "select order_item_id, order_id, product_id, quantity, subtotal "
                + "from order_items limit 1000;";
        return jdbcTemplate.query(sql, new OrderItemMapper());
    }

    @Override
    @Transactional
    public OrderItem findById(Long order_item_id) {

        final String sql = "select order_item_id, order_id, product_id, quantity, subtotal  "
                + "from order_items "
                + "where order_item_id = ?;";

        OrderItem orderItem = jdbcTemplate.query(sql, new OrderItemMapper(), order_item_id).stream()
                .findFirst().orElse(null);

        return orderItem;
    }

    @Override
    public OrderItem add(OrderItem orderItem) {

        final String sql = "insert into order_items (order_id, product_id, quantity, subtotal)"
                + " values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, orderItem.getOrderId());
            ps.setLong(2, orderItem.getProductId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setBigDecimal(4, orderItem.getSubtotal());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        orderItem.setOrderItemId(keyHolder.getKey().longValue());
        return orderItem;
    }

    @Override
    public boolean update(OrderItem orderItem) {

        final String sql = "update orderItem set order_id = ?, product_id = ?, quantity = ?, subtotal = ? where order_item_id = ?;";
        int res = jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getSubtotal(), orderItem.getOrderItemId());
        if (res == 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean deleteById(Long orderItemId) {
        return jdbcTemplate.update("delete from order_items where cart_id = ?;", orderItemId) > 0;
    }
}