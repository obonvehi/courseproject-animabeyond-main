package cat.tecnocampus.productapi.application.dto;

public class Order_ProductDTO {

    private String order_id;
    private String product_id;
    private String product_name;
    private int amount;

    public Order_ProductDTO(){}
    public Order_ProductDTO(int amount, String product_id, String product_name, String order_id){
        this.amount = amount;
        this.product_id = product_id;
        this.product_name = product_name;
        this.order_id = order_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
