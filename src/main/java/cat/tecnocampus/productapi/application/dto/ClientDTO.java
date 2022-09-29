package cat.tecnocampus.productapi.application.dto;

import java.util.List;

public class ClientDTO {

    private String id;
    private String username;
    private String email;
    private String password;
    private List<ProductDTO> subscribedProducts;

    public ClientDTO(){}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() {  return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword(){ return this.password; }
    public void setPassword(String password) { this.password = password; }

    public List<ProductDTO> getSubscribedProducts() {
        return subscribedProducts;
    }
    public void setSubscribedProducts(List<ProductDTO> subscribedProducts) {
        this.subscribedProducts = subscribedProducts;
    }
}
