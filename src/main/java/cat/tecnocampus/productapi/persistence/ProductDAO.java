package cat.tecnocampus.productapi.persistence;

import cat.tecnocampus.productapi.application.dto.ProductDTO;
import cat.tecnocampus.productapi.application.dto.ProductPriceDTO;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductDAO implements cat.tecnocampus.productapi.application.ProductDAO {
    private JdbcTemplate jdbcTemplate;

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractorImpl<ProductDTO> allProductsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(ProductDTO.class);

    RowMapperImpl<ProductDTO> oneProductRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(ProductDTO.class);

    ResultSetExtractorImpl<ProductPriceDTO> allProductsPriceRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(ProductPriceDTO.class);

    RowMapperImpl<ProductPriceDTO> oneProductPriceRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(ProductPriceDTO.class);

    @Override
    public List<ProductDTO> getAllProducts() {
        final var sql = "select * from product";
        return jdbcTemplate.query(sql, allProductsRowMapper);
    }

    @Override
    public ProductDTO getProductById(String id) {
        var sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(sql, oneProductRowMapper, id);
    }

    @Override
    public List<ProductPriceDTO> getAllProductPrices() {
        var sql = "select id, price from product";
        return jdbcTemplate.query(sql, allProductsPriceRowMapper);
    }

    @Override
    public ProductPriceDTO getProductPriceById(String id) {
        var sql = "select id, price from product where id = ?";
        return jdbcTemplate.queryForObject(sql, oneProductPriceRowMapper, id);
    }

    @Override
    public void updateProductPrice(ProductDTO productDTO) {
        final String updatePrice = "UPDATE product SET price = ? where id = ?";
        jdbcTemplate.update(updatePrice, productDTO.getPrice(), productDTO.getId());
    }
}
