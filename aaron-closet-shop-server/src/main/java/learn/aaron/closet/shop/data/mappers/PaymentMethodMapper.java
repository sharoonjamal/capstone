package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Order;
import learn.aaron.closet.shop.models.PaymentMethod;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMethodMapper implements RowMapper<PaymentMethod> {
    @Override
    public PaymentMethod mapRow(ResultSet resultSet, int i) throws SQLException {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setPaymentMethodId(resultSet.getLong("payment_method_id"));
        paymentMethod.setName(resultSet.getString("name"));
        paymentMethod.setDescription(resultSet.getString("descritption"));
        return paymentMethod;
    }

}
