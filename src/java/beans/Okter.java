package beans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("list")
@SessionScoped

public class Okter implements Serializable {

    ArrayList<TreningsOkt> liste = new ArrayList<TreningsOkt>();
    

    public void regNyOkt(TreningsOkt okt) {
        liste.add(okt);
    }
    public ArrayList<TreningsOkt> getListe() { return liste; }
}
