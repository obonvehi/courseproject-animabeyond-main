package cat.tecnocampus.productapi.domain;

import java.util.HashMap;

public class Order {

    private String id;
    private String period;
    private int state;

    private HashMap<String, Integer> productList; //id | quantity

    public Order(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public HashMap<String, Integer> getProductList(){ return productList; }

    public void setProductList(HashMap<String, Integer> productList){
        this.productList = productList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
