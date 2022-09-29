package cat.tecnocampus.productapi.application.dto;

import java.util.*;

public class OrderDTO {

    private String id;
    private String client_id;
    private Date creation_date;
    private Date delivery_date;
    private Date closing_date;
    private double total_price;
    private int state;

    private List<Order_ProductDTO> productList;

    public OrderDTO(){}

    public OrderDTO(String clientId, Date creationDate){
        this.id = UUID.randomUUID().toString();
        this.client_id = clientId;
        this.creation_date = creationDate;
        productList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    /*
    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }

     */

    public List<Order_ProductDTO> getProductList(){
        return productList;
    }
    public void setProductList(List<Order_ProductDTO> productList){
        this.productList = productList;
    }

    public Object getClient_id() {
        return client_id;
    }
    public void setClient_id(String client_id){
        this.client_id = client_id;
    }

    public Date getCreation_date() {
        return creation_date;
    }
    public void setCreation_date(Date creation_date){
        this.creation_date = creation_date;
    }

    public Date getDelivery_date() {
        return delivery_date;
    }
    public void setDelivery_date(Date delivery_date){
        this.delivery_date = delivery_date;
    }

    public Date getClosing_date() {
        return closing_date;
    }
    public void setClosing_date(Date closing_date) {
        this.closing_date = closing_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
