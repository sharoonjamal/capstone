package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.CategoryMapper;
import learn.aaron.closet.shop.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryJdbcTemplateRepository implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        final String sql = "select category_id, category_name, description, parent_category_id "
                + "from categories limit 1000;";
        return jdbcTemplate.query(sql, new CategoryMapper());
    }

    @Override
    @Transactional
    public Category findById(Long category_id) {

        final String sql = "select category_id, category_name, description, parent_category_id  "
                + "from categories "
                + "where category_id = ?;";

        Category category = jdbcTemplate.query(sql, new CategoryMapper(), category_id).stream()
                .findFirst().orElse(null);

        return category;
    }

    @Override
    public Category add(Category category) {

        final String sql = "insert into categories (category_name, description, parent_category_id)"
                + " values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setLong(3, category.getParentCategoryId());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        category.setCategoryId(keyHolder.getKey().longValue());
        return category;
    }

    @Override
    public boolean update(Category category) {

        final String sql = "update categories set category_name = ?, description = ?, parent_category_id = ? where category_id = ?;";
        int res = jdbcTemplate.update(sql, category.getCategoryName(), category.getDescription(), category.getParentCategoryId(), category.getCategoryId());
        if (res == 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean deleteById(Long categoryId) {
        return jdbcTemplate.update("delete from categories where category_id = ?;", categoryId) > 0;
    }
}

