package cat.tecnocampus.productapi.emailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender emailSender;

    public EmailSender(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    //To: client, Subject: Order generated, Text: products in order?
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("dani.fm17@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            System.err.print("Pont 1");

            emailSender.send(message);
            System.err.print("Pont 2");
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }
}
