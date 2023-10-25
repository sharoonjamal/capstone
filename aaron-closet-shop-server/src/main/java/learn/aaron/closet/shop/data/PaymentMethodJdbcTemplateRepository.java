package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.PaymentMethodRepository;
import learn.aaron.closet.shop.data.mappers.CartMapper;
import learn.aaron.closet.shop.data.mappers.PaymentMethodMapper;
import learn.aaron.closet.shop.models.Cart;
import learn.aaron.closet.shop.models.PaymentMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

@Repository
public class PaymentMethodJdbcTemplateRepository implements PaymentMethodRepository {
    private final JdbcTemplate jdbcTemplate;

    public PaymentMethodJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PaymentMethod> findAll() {
        final String sql = "select payment_method_id, name, description "
                + "from cart limit 1000;";
        return jdbcTemplate.query(sql, new PaymentMethodMapper());
    }

    @Override
    @Transactional
    public PaymentMethod findById(Long cart_id) {

        final String sql = "select payment_method_id, name, description  "
                + "from payment_methods "
                + "where payment_method_id = ?;";

        PaymentMethod paymentMethod = jdbcTemplate.query(sql, new PaymentMethodMapper(), cart_id).stream()
                .findFirst().orElse(null);

        return paymentMethod;
    }

    @Override
    public PaymentMethod add(PaymentMethod paymentMethod) {

        final String sql = "insert into payment_methods (name, description)"
                + " values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, paymentMethod.getName());
            ps.setString(2, paymentMethod.getDescription());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        paymentMethod.setPaymentMethodId(keyHolder.getKey().longValue());
        return paymentMethod;
    }

    @Override
    public boolean update(PaymentMethod paymentMethod) {

        final String sql = "update paymentMethod set name = ?, description = ? where payment_method_id = ?;";
        int res = jdbcTemplate.update(sql, paymentMethod.getName(), paymentMethod.getDescription(), paymentMethod.getPaymentMethodId());
        if (res == 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean deleteById(Long paymentMethodId) {
        return jdbcTemplate.update("delete from payment_methods where payment_method_id = ?;", paymentMethodId) > 0;
    }
}