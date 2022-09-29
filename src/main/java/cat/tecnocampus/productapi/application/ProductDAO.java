package cat.tecnocampus.productapi.application;

import cat.tecnocampus.productapi.application.dto.ProductDTO;
import cat.tecnocampus.productapi.application.dto.ProductPriceDTO;

import java.util.List;

public interface ProductDAO {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(String id);

    List<ProductPriceDTO> getAllProductPrices();

    ProductPriceDTO getProductPriceById(String id);

    void updateProductPrice(ProductDTO productDTO);
}
