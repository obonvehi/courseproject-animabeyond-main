package cat.tecnocampus.productapi.application;

import cat.tecnocampus.productapi.application.dto.*;
import cat.tecnocampus.productapi.emailSender.EmailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StoreController {

    private ClientDAO clientDAO;
    private ProductDAO productDAO;
    private EmailSender emailSender;

    public StoreController(ClientDAO clientDAO, ProductDAO productDAO, JavaMailSender javaMailSender) {
        this.clientDAO = clientDAO;
        this.productDAO = productDAO;
        this.emailSender = new EmailSender(javaMailSender);
    }

    public ClientDTO getClientByUsername(String username) {
        return clientDAO.getClientByUsername(username);
    }

    public List<ClientDTO> getClients() {
        return clientDAO.getAllClients();
    }

    public ClientDTO getClient(String id) {
        return clientDAO.getClientById(id);
    }

    public ClientDTO addClient(ClientDTO clientDTO) {
        clientDTO.setId(UUID.randomUUID().toString());
        return clientDAO.addClient(clientDTO);
    }

    public boolean isSubscribed(String c_id, String p_id) {
        return clientDAO.isSubscribed(c_id, p_id);
    }

    public void subscribe(String c_id, String p_id, int quantity) {
        clientDAO.subscribe(c_id, p_id, quantity);
    }

    public void updateSubscription(String c_id, String p_id, int quantity) {
        clientDAO.updateSubscription(c_id, p_id, quantity);
    }

    public void deleteSubscription(String c_id, String p_id) {
        clientDAO.deleteSubscription(c_id, p_id);
    }

    public List<SubscriptionDTO> getSubscriptionsOrderedByClient() {
        return clientDAO.getSubscriptionsOrdered();
    }

    public OrderDTO getOrder(String o_id) {
        OrderDTO orderDTO = clientDAO.getOrder(o_id);
        List<Order_ProductDTO> productList = clientDAO.getProductsFromOrder(o_id);
        List<Order_ProductDTO> orderProductList = new ArrayList<>();

        for(Order_ProductDTO op : productList){
            orderProductList.add(op);
        }

        orderDTO.setProductList(orderProductList);

        return orderDTO;
    }

    public OrderDTO createOrder(OrderDTO o_DTO) {
        return clientDAO.createOrder(o_DTO);
    }

    public ProductDTO getProduct(String p_id) {
        return clientDAO.getProduct(p_id);
    }

    public void addProductToOrder(String orderId, String productId, int amount) {
        clientDAO.addProductToOrder(orderId, productId, amount);
    }

    public void updateProductInOrder(String o_id, String p_id, int amount) throws Exception {
        if(getOrder(o_id).getState()!=0){
            throw new Exception("Order is closed");
        }
        else{
            clientDAO.updateOrderProduct(o_id, p_id, amount);
            OrderDTO order = getOrder(o_id);
            order.setTotal_price(calculateTotalPrice(order.getProductList()));
            updateOrder(order);
        }
    }

    private void updateOrder(OrderDTO order){
        clientDAO.updateOrder(order);
    }

    private void updateProductPrice(ProductDTO productDTO) {
        productDAO.updateProductPrice(productDTO);
    }

    private List<OrderDTO> getAllOrders() {
        return clientDAO.getAllOrders();
    }

    public List<Order_ProductDTO> getProductsOrder(String order_id){
        return clientDAO.getProductsFromOrder(order_id);
    }

    public List<OrderDTO> getAllOrdersClient(String c_id) {
        List<OrderDTO> orders = clientDAO.getAllOrdersClient(c_id);

        for(OrderDTO order : orders){
            for(Order_ProductDTO product : getProductsOrder(order.getId())){
                order.getProductList().add(product);
            }
        }
        return orders;
    }

    public void generateOrders() {
        List<SubscriptionDTO> subs = getSubscriptionsOrderedByClient();
        Map <String, String> productsInOrder = new HashMap<>(); //Products (name) - Amount
        OrderDTO order = null;
        String currentClientId = "";
        Calendar currentDate;
        Calendar initialDate = Calendar.getInstance();

        for (int i = 0; i < subs.size(); i++) {
            currentDate = Calendar.getInstance();
            initialDate.setTime(getProduct(subs.get(i).getIdProduct()).getInitial_date());

            if ((initialDate.get(Calendar.WEEK_OF_YEAR) - currentDate.get(Calendar.WEEK_OF_YEAR)) % 2 == 0) {
                if (!currentClientId.equals(subs.get(i).getIdClient())) {
                    order.setTotal_price(calculateTotalPrice(order.getProductList()));
                    calculateDeliveryDate(order);
                    calculateClosingDate(order);
                    createOrder(order);
                    sendOrderEmail(getClient(currentClientId).getEmail(), productsInOrder);

                    productsInOrder.clear();
                    currentClientId = subs.get(i).getIdClient();
                    order = new OrderDTO(currentClientId, new Date());
                }
                productsInOrder.put(getProduct(subs.get(i).getIdProduct()).getName(),""+ subs.get(i).getQuantity());
                addProductToOrder(order.getId(), getProduct(subs.get(i).getIdProduct()).getName(), subs.get(i).getQuantity());
            }
        }
    }

    private double calculateTotalPrice(List<Order_ProductDTO> products){
        double price = 0.0;
        for(Order_ProductDTO product : products){
            if(productDAO.getProductById(product.getProduct_id()).getVat_type().equals("IVA 4% (Bienes)")){
                price += ((productDAO.getProductById(product.getProduct_id()).getPrice()) * 1.04)*product.getAmount(); //El  * 1.04 es el 4% IVA
            }
            else if(productDAO.getProductById(product.getProduct_id()).getVat_type().equals("IVA 10% (Bienes)")){
                price += ((productDAO.getProductById(product.getProduct_id()).getPrice()) * 1.10)*product.getAmount(); //El  * 1.10 es el 10% IVA
            }

            else if(productDAO.getProductById(product.getProduct_id()).getVat_type().equals("IVA 21% (Bienes)")){
                price += ((productDAO.getProductById(product.getProduct_id()).getPrice()) * 1.21)*product.getAmount(); //El  * 1.21 es el 21% IVA
            }
        }

        return price;
    }

    private void calculateDeliveryDate(OrderDTO _order) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(_order.getCreation_date());
        cal.add(Calendar.DATE, 10);
        _order.setDelivery_date(cal.getTime());
    }

    private void calculateClosingDate(OrderDTO _order) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(_order.getCreation_date());
        cal.add(Calendar.DATE, 4);
        _order.setClosing_date(cal.getTime());
    }

    private void sendOrderEmail(String email, Map <String, String> productsInOrder){
        String message = "Products:\n--------";
        for (Map.Entry<String, String> entry : productsInOrder.entrySet()) {
            message = "\n"+entry.getKey()+": "+entry.getValue()+" units.";
        }
        productsInOrder.clear();
        emailSender.sendSimpleMessage(email, "ORDER GENERATED", message);
    }

    public void deliverOrders() {
        for (OrderDTO order: getAllOrders()) {
            if(order.getState()==1){
                order.setState(2);
                updateOrder(order);
            }
        }
    }

    public void updateProductPrices() {
        List<ProductPriceDTO> prices = productDAO.getAllProductPrices();
        ProductDTO productDTO;

        for (ProductPriceDTO price: prices) {
            productDTO = getProduct(price.getId());
            productDTO.setPrice(price.getPrice());
            updateProductPrice(productDTO);
        }

    }

    public void closeOrders() {
        Calendar currentDate = Calendar.getInstance();
        Calendar creationDate = Calendar.getInstance();

        for (OrderDTO order: getAllOrders()) {
            if(currentDate.DAY_OF_WEEK - 4 == creationDate.DAY_OF_WEEK){
                order.setState(1);
                updateOrder(order);
            }
        }
    }

    public void provaScheduler() {
        System.err.println("PROVA SCHEDULER: "+ LocalDateTime.now());
    }
}