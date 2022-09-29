package cat.tecnocampus.productapi.api;


import cat.tecnocampus.productapi.application.dto.Order_ProductDTO;
import cat.tecnocampus.productapi.application.StoreController;
import cat.tecnocampus.productapi.application.dto.ClientDTO;
import cat.tecnocampus.productapi.application.dto.OrderDTO;
import cat.tecnocampus.productapi.emailSender.EmailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class StoreRESTController {

    StoreController storeController;
    EmailSender emailSender;

    public StoreRESTController(StoreController storeController, JavaMailSender emailSender){
        this.storeController = storeController;
        this.emailSender = new EmailSender(emailSender);
    }

    @GetMapping("/getIdByUsername/{name}")
    public ClientDTO getIdByUsername(@PathVariable String name){
        return storeController.getClientByUsername(name);
    }

    @PostMapping("/register")
    public ClientDTO addClient(@RequestBody ClientDTO clientDTO){
        return storeController.addClient(clientDTO);
    }

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return storeController.getClients();
    }

    @GetMapping("clients/me")
    public ClientDTO getMyClient(Principal principal) {
        return storeController.getClientByUsername(principal.getName());
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable String id){
        return storeController.getClient(id);
    }

    @GetMapping("/clients/{c_id}/products/{p_id}")
    public boolean isSubscribed(@PathVariable String c_id, @PathVariable String p_id){
        return storeController.isSubscribed(c_id, p_id);
    }

    @PostMapping("/clients/{c_id}/products/{p_id}")
    public void subscribe(@RequestParam Map<String, String> _queryParameters, @PathVariable String c_id, @PathVariable String p_id){
        if(_queryParameters.containsKey("quantity")){
            int quantity = Integer.parseInt(_queryParameters.get("quantity"));
            if(storeController.isSubscribed(c_id, p_id)){
                if(quantity == 0){
                    storeController.deleteSubscription(c_id, p_id);
                }else{
                    storeController.updateSubscription(c_id, p_id, quantity);
                }
            }else{
                storeController.subscribe(c_id, p_id, quantity);
            }
        }
    }

    @GetMapping("/clients/orders/{o_id}")
    public OrderDTO getOrder(@PathVariable String o_id){
        return storeController.getOrder(o_id);
    }

    @GetMapping("/clients/orders/{o_id}/products")
    public List<Order_ProductDTO> getProductsOrder(@PathVariable String o_id){
        return storeController.getProductsOrder(o_id);
    }

    @GetMapping("/clients/{c_id}/orders")
    public List<OrderDTO> getOrdersClient(@PathVariable String c_id){
        return storeController.getAllOrdersClient(c_id);
    }

    @PostMapping("/clients/orders/{o_id}/products/{p_id}")
    public void updateOrderProduct(@RequestParam Map<String, String> _queryParameters, @PathVariable String o_id, @PathVariable String p_id) throws Exception {

        if(_queryParameters.containsKey("quantity")){
            int quantity = Integer.parseInt(_queryParameters.get("quantity"));
            storeController.updateProductInOrder(o_id, p_id, quantity);
        }
    }

    @GetMapping("/testMail")
    public void testMail(){
        emailSender.sendSimpleMessage("dani.fm17@gmail.com", "Prueba", "esto es una prueba");
    }
}
