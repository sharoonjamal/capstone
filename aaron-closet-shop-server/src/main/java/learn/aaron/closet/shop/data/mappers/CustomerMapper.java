package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

    public class CustomerMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            Customer customer = new Customer();
            customer.setCustomerId(resultSet.getLong("customer_id"));
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setLastName(resultSet.getString("last_name"));
            customer.setEmail(resultSet.getString("email"));
            customer.setPassword(resultSet.getString("password"));
            customer.setShippingAddress(resultSet.getString("shipping_address"));
            customer.setBillingAddress(resultSet.getString("billing_address"));
            customer.setPhoneNumber(resultSet.getString("phone_number"));
            return customer;
        }
    }

