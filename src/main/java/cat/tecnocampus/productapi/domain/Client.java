package cat.tecnocampus.productapi.domain;
import java.util.UUID;

public class Client {

    private String id;
    private String username;
    private String email;
    private String password;

    public Client(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {  return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword(){ return this.password; }
    public void setPassword(String password) { this.password = password; }


    @Override
    public boolean equals(Object obj) {
        Client c = (Client) obj;
        if (this.getId()==c.getId())
            return true;
        else
            return false;
    }

}
