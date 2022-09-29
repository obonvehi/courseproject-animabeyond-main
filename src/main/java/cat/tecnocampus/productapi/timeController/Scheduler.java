package cat.tecnocampus.productapi.timeController;

import cat.tecnocampus.productapi.application.StoreController;
import cat.tecnocampus.productapi.persistence.ClientDAO;
import cat.tecnocampus.productapi.persistence.ProductDAO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Scheduler {
    StoreController storeController;

    public Scheduler(ClientDAO clientDAO, ProductDAO productDAO, JavaMailSender mailSender){
        storeController = new StoreController(clientDAO, productDAO, mailSender);
    }

    @Scheduled(cron = "0 0 2 ? * MON")
    public void generateOrders(){
        storeController.generateOrders();
    }

    @Scheduled(cron = "0 45 23 ? * FRI")
    public void closeOrders(){
        storeController.closeOrders();
    }

    @Scheduled(cron = "0 0 12 ? * THU")
    public void deliverOrders(){
        storeController.deliverOrders();
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateProductPrices(){
        storeController.updateProductPrices();
    }

    @Scheduled(cron = "*/60 * * * * *")
    public void prova(){
        storeController.provaScheduler();
    }
}
