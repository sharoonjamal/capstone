package learn.aaron.closet.shop.data;

import learn.aaron.closet.shop.data.mappers.ImageMapper;
import learn.aaron.closet.shop.models.Image;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


@Repository
public class ImageJdbcTemplateRepository implements ImageRepository {

    private final JdbcTemplate jdbcTemplate;

    public ImageJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Image> findAll() {
        final String sql = "select image_id, product_id, image_url, caption "
                + "from images limit 1000;";
        return jdbcTemplate.query(sql, new ImageMapper());
    }

    @Override
    @Transactional
    public Image findById(Long image_id) {

        final String sql = "select image_id, product_id, image_url, caption  "
                + "from images "
                + "where image_id = ?;";

        Image image = jdbcTemplate.query(sql, new ImageMapper(), image_id).stream()
                .findFirst().orElse(null);

        return image;
    }

    @Override
    public Image add(Image image) {

        final String sql = "insert into images (product_id, image_url, caption)"
                + " values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, image.getProductId());
            ps.setString(2, image.getImageUrl());
            ps.setString(3, image.getCaption());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        image.setImageId(keyHolder.getKey().longValue());
        return image;
    }

    @Override
    public boolean update(Image image) {

        final String sql = "update images set product_id = ?, image_url = ?, caption = ? where image_id = ?;";
        int res = jdbcTemplate.update(sql, image.getProductId(), image.getImageUrl(), image.getCaption(), image.getImageId());
        if (res == 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public boolean deleteById(Long imageId) {
        return jdbcTemplate.update("delete from images where image_id = ?;", imageId) > 0;
    }
}