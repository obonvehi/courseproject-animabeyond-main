package cat.tecnocampus.productapi.application.dto;

import java.util.Random;

public class ProductPriceDTO {
    private String id;
    private double price;
    private Random random;

    public ProductPriceDTO() {
        this.random = new Random();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price + random.nextGaussian();
    }
}
