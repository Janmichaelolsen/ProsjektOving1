package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.Date;

@Named("list")
@SessionScoped

public class Okter implements Serializable {

    ArrayList<TreningsOkt> liste = new ArrayList<TreningsOkt>();
    
    public Okter(){
    }

    public void regNyOkt(int oktnr, String dato, int varighet, String kategori, String tekst) {
        TreningsOkt nyokt = new TreningsOkt(oktnr, dato, varighet, kategori, tekst);
        liste.add(nyokt);
    }
    public ArrayList getListe() { return liste; }
}
