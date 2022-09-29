package cat.tecnocampus.productapi.persistence;

import cat.tecnocampus.productapi.application.dto.*;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDAO implements cat.tecnocampus.productapi.application.ClientDAO{

    private JdbcTemplate jdbcTemplate;

    public ClientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSetExtractorImpl<ClientDTO> allClientsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(ClientDTO.class);

    RowMapperImpl<ClientDTO> oneClientRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(ClientDTO.class);

    RowMapperImpl<OrderDTO> oneOrderRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(OrderDTO.class);

    ResultSetExtractorImpl<OrderDTO> allOrdersRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(OrderDTO.class);

    RowMapperImpl<ProductDTO> oneProductRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(ProductDTO.class);

    ResultSetExtractorImpl<SubscriptionDTO> allSubscriptionRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("client_id, product_id")
                    .newResultSetExtractor(SubscriptionDTO.class);

    ResultSetExtractorImpl<Order_ProductDTO> allOrdersProductsRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("order_id, product_id")
                    .newResultSetExtractor(Order_ProductDTO.class);

    @Override
    public List<ClientDTO> getAllClients() {
        final var sql = "select id, username, email from client";
        return jdbcTemplate.query(sql, allClientsRowMapper);
    }

    @Override
    public ClientDTO getClientById(String id) {
        var sql = "select id, username, email from client where id = ?";
        return jdbcTemplate.queryForObject(sql, oneClientRowMapper, id);
    }

    @Override
    public ClientDTO getClientByUsername(String username) {
        var sql = "select id, username, email from client where username = ?";
        return jdbcTemplate.queryForObject(sql, oneClientRowMapper, username);
    }

    @Override
    public ClientDTO addClient(ClientDTO profile) {
        final String insertProfile = "INSERT INTO client (id, username, name, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertProfile, profile.getId(), profile.getUsername(), profile.getEmail());

        return this.getClientById(profile.getId());
    }

    @Override
    public boolean isSubscribed(String client_id, String product_id) {
        var sql = "select COUNT(*) from subscription where client_id = ? AND product_id = ?";
        int a = jdbcTemplate.queryForObject(sql, Integer.class, client_id, product_id);
        return a > 0 ? true : false;
    }

    @Override
    public void subscribe(String client_id, String product_id, int amount) {
        final String insertProfile = "INSERT INTO subscription (client_id, product_id, amount) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertProfile, client_id, product_id, amount);
    }

    @Override
    public void updateSubscription(String client_id, String product_id, int amount) {
        final String updateSubscription = "UPDATE subscription SET amount = ? where client_id = ? AND product_id = ?";
        jdbcTemplate.update(updateSubscription, amount, client_id, product_id);
    }

    @Override
    public void deleteSubscription(String client_id, String product_id) {
        final String deleteSubscription = "DELETE FROM subscription where client_id = ? AND product_id = ?";
        jdbcTemplate.update(deleteSubscription, client_id, product_id);
    }

    @Override
    public List<SubscriptionDTO> getSubscriptionsOrdered(){
        final var sql = "select * from client order by client_id";
        return jdbcTemplate.query(sql, allSubscriptionRowMapper);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        final String insertOrder = "INSERT INTO store_order (id, client_id, creation_date, delivery_date, closing_date, total_price) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertOrder, orderDTO.getId(), orderDTO.getClient_id(), orderDTO.getCreation_date(), orderDTO.getDelivery_date(), orderDTO.getClosing_date(), orderDTO.getTotal_price());
        return getOrder(orderDTO.getId());
    }

    @Override
    public OrderDTO getOrder(String order_id) {
        var sql = "select * from store_order where id = ?";
        return jdbcTemplate.queryForObject(sql, oneOrderRowMapper, order_id);
    }

    public List<Order_ProductDTO> getProductsFromOrder(String order_id){
        final var sql = "select * from order_product WHERE order_id = ?";
        return jdbcTemplate.query(sql, allOrdersProductsRowMapper, order_id);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        final var sql = "select * from store_order";
        return jdbcTemplate.query(sql, allOrdersRowMapper);
    }

    @Override
    public void updateOrder(OrderDTO orderDTO) {
        final String updateOrder = "UPDATE store_order SET client_id = ?, creation_date = ?, delivery_date = ?, closing_date = ?, total_price = ?, state = ? where id = ?";
        jdbcTemplate.update(updateOrder, orderDTO.getClient_id(), orderDTO.getCreation_date(), orderDTO.getDelivery_date(), orderDTO.getClosing_date(), orderDTO.getTotal_price(), orderDTO.getState(), orderDTO.getId());
    }

    @Override
    public List<OrderDTO> getAllOrdersClient(String client_id) {
        final var sql = "select * from store_order where client_id = ?";
        return jdbcTemplate.query(sql, allOrdersRowMapper, client_id);
    }

    @Override
    public ProductDTO getProduct(String p_id) {
        var sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(sql, oneProductRowMapper, p_id);
    }

    @Override
    public void addProductToOrder(String orderId, String productId, int amount) {
        final String insertProductToOrder = "INSERT INTO order_product (order_id, product_id, amout) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertProductToOrder, orderId, productId, amount);
    }

    @Override
    public void updateOrderProduct(String order_id, String product_id, int amount) {
        final String updateOrderProduct = "UPDATE order_product SET amount = ? where order_id = ? AND product_id = ?";
        jdbcTemplate.update(updateOrderProduct, amount, order_id, product_id);
    }
}
