package cat.tecnocampus.productapi.application;

import cat.tecnocampus.productapi.application.dto.*;

import java.util.List;
import java.util.Map;

public interface ClientDAO {
    List<ClientDTO> getAllClients();

    ClientDTO getClientById(String id);

    ClientDTO getClientByUsername(String username);

    ClientDTO addClient(ClientDTO profile);

    boolean isSubscribed(String client_id, String product_id);

    void subscribe(String client_id, String product_id, int amount);

    void updateSubscription(String client_id, String product_id, int amount);

    void deleteSubscription(String client_id, String product_id);

    public List<SubscriptionDTO> getSubscriptionsOrdered();

    public OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrder(String order_id);

    List<Order_ProductDTO> getProductsFromOrder(String order_id);

    List<OrderDTO> getAllOrdersClient(String client_id);

    public void addProductToOrder(String orderId, String productId, int amount);

    void updateOrderProduct (String order_id, String product_id, int amount) throws Exception;

    ProductDTO getProduct(String p_id);

    List<OrderDTO> getAllOrders();

    void updateOrder(OrderDTO orderDTO);

}
