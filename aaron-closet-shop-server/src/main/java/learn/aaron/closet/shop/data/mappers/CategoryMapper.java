package learn.aaron.closet.shop.data.mappers;

import learn.aaron.closet.shop.models.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        Category category = new Category();
        category.setCategoryId(resultSet.getLong("category_id"));
        category.setCategoryName(resultSet.getString("category_name"));
        category.setDescription(resultSet.getString("description"));
        category.setParentCategoryId(resultSet.getLong("parent_category_id"));
        return category;
    }
}
