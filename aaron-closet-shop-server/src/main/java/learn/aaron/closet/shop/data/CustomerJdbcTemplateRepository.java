package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.CustomerMapper;
import learn.aaron.closet.shop.models.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
     @Repository
    public class CustomerJdbcTemplateRepository implements CustomerRepository {

        private final JdbcTemplate jdbcTemplate;

        public CustomerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public List<Customer> findAll() {
            final String sql = "select customer_id, first_name, last_name, email, password, " +
                    "shipping_address, billing_address, phone_number "
                    + "from customers limit 1000;";
            return jdbcTemplate.query(sql, new CustomerMapper());
        }

        @Override
        @Transactional
        public Customer findById(Long customer_id) {

            final String sql = "select customer_id, first_name, last_name, email, password, " +
                    "shipping_address, billing_address, phone_number  "
                    + "from customers "
                    + "where customer_id = ?;";

            Customer customer = jdbcTemplate.query(sql, new CustomerMapper(), customer_id).stream()
                    .findFirst().orElse(null);

            return customer;
        }

        @Override
        public Customer add(Customer customer) {

            final String sql = "insert into customers (first_name, last_name, email, password, " +
                    "shipping_address, billing_address, phone_number)"
                    + " values (?,?,?,?,?,?,?);";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getPassword());
                ps.setString(5, customer.getShippingAddress());
                ps.setString(6, customer.getBillingAddress());
                ps.setString(7, customer.getPhoneNumber());


                return ps;
            }, keyHolder);

            if (rowsAffected <= 0) {
                return null;
            }

            customer.setCustomerId(keyHolder.getKey().longValue());
            return customer;
        }

        @Override
        public boolean update(Customer customer) {

            final String sql = "update customers set first_name = ?, last_name = ?, email = ?, password = ?," +
                    " shipping_address = ?, billing_address = ?, phone_number = ? where customer_id = ?;";
            int res = jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                    customer.getPassword(),customer.getShippingAddress(), customer.getBillingAddress(), customer.getPhoneNumber());
            if (res == 1){
                return true;
            }else {
                return false;
            }

        }

        @Override
        @Transactional
        public boolean deleteById(Long customerId) {
            return jdbcTemplate.update("delete from customers where customer_id = ?;", customerId) > 0;
        }
    }

