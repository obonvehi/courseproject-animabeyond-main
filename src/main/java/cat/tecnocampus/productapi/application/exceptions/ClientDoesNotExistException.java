package cat.tecnocampus.productapi.application.exceptions;

public class ClientDoesNotExistException extends RuntimeException{
    public ClientDoesNotExistException(String clientId){
        super("Client " + clientId + " doesn't exist");
    }
}
