package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.CartMapper;
import learn.aaron.closet.shop.models.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

    @Repository
    public class CartJdbcTemplateRepository implements CartRepository {

        private final JdbcTemplate jdbcTemplate;

        public CartJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public List<Cart> findAll() {
            final String sql = "select cart_id, customer_id, product_id, quantity, added_to_cart "
                    + "from cart limit 1000;";
            return jdbcTemplate.query(sql, new CartMapper());
        }

        @Override
        @Transactional
        public Cart findById(Long cart_id) {

            final String sql = "select cart_id, customer_id, product_id, quantity, added_to_cart  "
                    + "from cart "
                    + "where cart_id = ?;";

            Cart cart = jdbcTemplate.query(sql, new CartMapper(), cart_id).stream()
                    .findFirst().orElse(null);

            return cart;
        }

        @Override
        public Cart add(Cart cart) {

            final String sql = "insert into characters (customer_id, product_id, quantity, added_to_cart)"
                    + " values (?,?,?,?);";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, cart.getCustomerId());
                ps.setLong(2, cart.getProductId());
                ps.setInt(3, cart.getQuantity());
                ps.setTimestamp(4, cart.getAddedToCart());

                return ps;
            }, keyHolder);

            if (rowsAffected <= 0) {
                return null;
            }

            cart.setCartId(keyHolder.getKey().longValue());
            return cart;
        }

        @Override
        public boolean update(Cart cart) {

            final String sql = "update cart set customer_id = ?, product_id = ?, quantity = ?, added_to_cart = ? where cart_id = ?;";
            int res = jdbcTemplate.update(sql, cart.getCustomerId(), cart.getProductId(), cart.getQuantity(),cart.getAddedToCart(), cart.getCartId());
            if (res == 1){
                return true;
            }else {
                return false;
            }

        }

        @Override
        @Transactional
        public boolean deleteById(Long cartId) {
            return jdbcTemplate.update("delete from cart where cart_id = ?;", cartId) > 0;
        }
    }

