package beans;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("verktoy")
@SessionScoped

public class Verktoy implements Serializable{
    Databehandler db = new Databehandler();

}
