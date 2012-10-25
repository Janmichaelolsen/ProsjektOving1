package beans;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("behandler")
@SessionScoped

public class Databehandler implements Serializable{
    private String dato = "";
    private int varighet;
    private Okter okter = new Okter();
    private String kategori;
    private String tekst;
    Date date = new Date();
    private int oktnummer = okter.liste.size()+1;
    
    public Databehandler(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        dato += sdf.format(date);
}
    public void regOkt(){
        okter.regNyOkt(oktnummer, dato, varighet, kategori, tekst);
    }
    
    public int getOktnummer() { return oktnummer; }
    
    public String getDato() { return dato; }

    public String getKategori() { return kategori; }

    public String getTekst() { return tekst; }

    public int getVarighet() { return varighet; }

    
    public void setDato(String ny) { dato = ny; }
    
    public void setKategori(String ny) { kategori = ny; }

    public void setOktnummer(int ny) { oktnummer = ny;  }

    public void setTekst(String ny) { tekst = ny; }

    public void setVarighet(int ny) { varighet = ny; }

}
