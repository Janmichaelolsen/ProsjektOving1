package beans;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("behandler")
@SessionScoped

public class Databehandler implements Serializable{
    private String tdato = "";
    private int tvarighet;
    private Okter okter = new Okter();
    private String tkategori;
    private String ttekst;
    Date date = new Date();
    private int toktnummer = 1;
    
    public Databehandler(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tdato += sdf.format(date);
    }
    public void regOkt(){
        TreningsOkt nyokt = new TreningsOkt(toktnummer, tdato, tvarighet, tkategori, ttekst);
        okter.regNyOkt(nyokt);
        toktnummer++;
    }
    public ArrayList getListe(){ return okter.getListe();}
    
    public Okter getOkter() { return okter;}
    
    public int getOktnummer() { return toktnummer; }
    
    public String getDato() { return tdato; }

    public String getKategori() { return tkategori; }

    public String getTekst() { return ttekst; }

    public int getVarighet() { return tvarighet; }

    
    public void setDato(String ny) { tdato = ny; }
    
    public void setKategori(String ny) { tkategori = ny; }

    public void setOktnummer(int ny) { toktnummer = ny;  }

    public void setTekst(String ny) { ttekst = ny; }

    public void setVarighet(int ny) { tvarighet = ny; }

}
