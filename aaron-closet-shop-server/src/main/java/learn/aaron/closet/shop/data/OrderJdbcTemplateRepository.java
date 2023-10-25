package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.OrderMapper;
import learn.aaron.closet.shop.models.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


    @Repository
    public class OrderJdbcTemplateRepository implements OrderRepository {

        private final JdbcTemplate jdbcTemplate;

        public OrderJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public List<Order> findAll() {
            final String sql = "select order_id, customer_id, order_date, order_status, total_amount, shipping_method, payment_method "
                    + "from orders limit 1000;";
            return jdbcTemplate.query(sql, new OrderMapper());
        }

        @Override
        @Transactional
        public Order findById(Long order_id) {

            final String sql = "select order_id, customer_id, order_date, order_status, total_amount, shipping_method, payment_method "
                    + "from orders "
                    + "where order_id = ?;";

            Order order = jdbcTemplate.query(sql, new OrderMapper(), order_id).stream()
                    .findFirst().orElse(null);

            return order;
        }

        @Override
        public Order add(Order order) {

            final String sql = "insert into orders (customer_id, order_date, order_status," +
                    " total_amount, shipping_method, payment_method)"
                    + " values (?,?,?,?,?,?);";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, order.getCustomerId());
                ps.setDate(2, order.getOrderDate());
                ps.setString(3, order.getOrderStatus());
                ps.setBigDecimal(4, order.getTotalAmount());
                ps.setString(5, order.getShippingMethod());
                ps.setString(6, order.getPaymentMethod());

                return ps;
            }, keyHolder);

            if (rowsAffected <= 0) {
                return null;
            }

            order.setOrderId(keyHolder.getKey().longValue());
            return order;
        }

        @Override
        public boolean update(Order order) {

            final String sql = "update orders set order_id = ?, customer_id = ?, order_date = ?, order_status = ?," +
                    " total_amount = ?, shipping_method = ?, payment_method = ? where character_id = ?;";
            int res = jdbcTemplate.update(sql, order.getOrderId(), order.getCustomerId(), order.getOrderDate(),
                    order.getOrderStatus(),order.getTotalAmount(), order.getShippingMethod(), order.getPaymentMethod());
            if (res == 1){
                return true;
            }else {
                return false;
            }

        }

        @Override
        @Transactional
        public boolean deleteById(Long orderId) {
            return jdbcTemplate.update("delete from orders where order_id = ?;", orderId) > 0;
        }
    }

