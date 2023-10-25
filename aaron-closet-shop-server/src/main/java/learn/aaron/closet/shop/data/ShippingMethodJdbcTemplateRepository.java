package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.ShippingMethodMapper;
import learn.aaron.closet.shop.models.ShippingMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ShippingMethodJdbcTemplateRepository implements ShippingMethodRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShippingMethodJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ShippingMethod> findAll() {
        final String sql = "select shipping_method_id, name, description, cost "
                + "from products limit 1000;";
        return jdbcTemplate.query(sql, new ShippingMethodMapper());
    }

    @Override
    @Transactional
    public ShippingMethod findById(Long shipping_method_id) {

        final String sql = "select shipping_method_id, name, description, cost  "
                + "from shipping_methods "
                + "where shipping_method_id = ?;";

        ShippingMethod shippingMethod = jdbcTemplate.query(sql, new ShippingMethodMapper(), shipping_method_id).stream()
                .findFirst().orElse(null);

        return shippingMethod;
    }

    @Override
    public ShippingMethod add(ShippingMethod shippingMethod) {

        final String sql = "insert into shipping_methods (name, description, cost)"
                + " values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, shippingMethod.getName());
            ps.setString(2, shippingMethod.getDescription());
            ps.setBigDecimal(3, shippingMethod.getCost());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        shippingMethod.setShippingMethodId(keyHolder.getKey().longValue());
        return shippingMethod;
    }

    @Override
    public boolean update(ShippingMethod shippingMethod) {

        final String sql = "update shipping_methods set name = ?, description = ?, cost = ? where shipping_method_id = ?;";
        int res = jdbcTemplate.update(sql, shippingMethod.getName(), shippingMethod.getDescription(), shippingMethod.getCost(),
                shippingMethod.getShippingMethodId());
        if (res == 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean deleteById(Long shippingMethodId) {
        return jdbcTemplate.update("delete from shipping_methods where shipping_method_id = ?;", shippingMethodId) > 0;
    }
}