package cat.tecnocampus.productapi.api;

import cat.tecnocampus.productapi.application.dto.ProductDTO;
import cat.tecnocampus.productapi.application.ProductDAO;
import cat.tecnocampus.productapi.application.dto.ProductPriceDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class GetProductsREST {
    private ProductDAO productDAO;

    public GetProductsREST(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProductById(@PathVariable String id) {
        return productDAO.getProductById(id);
    }

    @GetMapping("products/price")
    public List<ProductPriceDTO> getProductsPriceById() {
        return productDAO.getAllProductPrices();
    }

    @GetMapping("/products/{id}/price")
    public ProductPriceDTO getProductPriceById(@PathVariable String id) {
        return productDAO.getProductPriceById(id);
    }

    @GetMapping("/categories")
    public String getCategories() {
        return "[\"Ous | Huevos\", \"All\", \"Altres\", \"Verdura\", \"Oli, vinagre | Aceite, vinagre\", \"Begudes alcohòliques | Bebidas alcohólicas\", \"Cereals i llegums\", \"Pa i pastisseria\", \"Liqüats Vegetals | Licuados Vegetales\", \"Peix  | Pescado\", \"Làctics | Lácticos\", \"Carn i peix\", \"Proteïna vegetal\", \"Cosmètica, salut | Cosmética, salud \", \"Publicacions | Publicaciones\", \"Fruita | Fruta\", \"Fuits secs | Frutos secos\"]";
    }
}
