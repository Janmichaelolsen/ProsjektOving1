package beans;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named("login")
@SessionScoped
public class Login implements Serializable{
    private String username;
    private String password;
    
public String getUsername(){ return username; }
public String getPassword(){ return password; }

public void setUsername(String ny){ username = ny;}
public void setPassword(String ny){ password = ny;}

}
