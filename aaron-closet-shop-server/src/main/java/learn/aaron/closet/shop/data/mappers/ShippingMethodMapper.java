package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.ShippingMethod;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippingMethodMapper implements RowMapper<ShippingMethod> {
    @Override
    public ShippingMethod mapRow(ResultSet resultSet, int i) throws SQLException {
        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.setShippingMethodId(resultSet.getLong("payment_method_id"));
        shippingMethod.setName(resultSet.getString("name"));
        shippingMethod.setDescription(resultSet.getString("description"));
        shippingMethod.setCost(resultSet.getBigDecimal("cost"));

        return shippingMethod;
    }
}
