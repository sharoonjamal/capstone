package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.ProductMapper;
import learn.aaron.closet.shop.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProductJdbcTemplateRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        final String sql = "select product_id, name, description, price, stock_quantity, image_url, manufacturer "
                + "from products limit 1000;";
        return jdbcTemplate.query(sql, new ProductMapper());
    }

    @Override
    @Transactional
    public Product findById(Long product_id) {

        final String sql = "select product_id, name, description, price, stock_quantity, image_url, manufacturer  "
                + "from products "
                + "where product_id = ?;";

        Product product = jdbcTemplate.query(sql, new ProductMapper(), product_id).stream()
                .findFirst().orElse(null);

        return product;
    }

    @Override
    public Product add(Product product) {

        final String sql = "insert into products ( name, description, price, stock_quantity, image_url, manufacturer)"
                + " values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setLong(4, product.getStockQuantity());
            ps.setString(5, product.getImageUrl());
            ps.setString(6, product.getManufacturer());


            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        product.setProductId(keyHolder.getKey().longValue());
        return product;
    }

    @Override
    public boolean update(Product product) {

        final String sql = "update products set name = ?, description = ?, price = ?, stock_quantity = ?, image_url = ? where product_id = ?;";
        int res = jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getPrice(), product.getStockQuantity(),product.getImageUrl(), product.getProductId());
        if (res == 1){
            return true;
        }else {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean deleteById(Long productId) {
        return jdbcTemplate.update("delete from products where product_id = ?;", productId) > 0;
    }
}
