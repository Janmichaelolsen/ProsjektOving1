package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.Date;
import java.util.Locale;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

@Named("variables")
@SessionScoped

public class TreningsOkt implements Serializable{
    private String brukernavn;
    private String passord;
    private int oktnummer;
    private String dato = "";
    private int varighet;
    private String kategori;
    private String tekst;
    Date date = new Date();

    public TreningsOkt(){
        oktnummer++;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        dato += sdf.format(date);
    }
    public String getBrukernavn() { return brukernavn;}
    
    public String getPassord() { return passord; }
    
    public int getOktnummer() { return oktnummer; }
    
    public String getDato() { return dato; }

    public String getKategori() { return kategori; }

    public String getTekst() { return tekst; }

    public int getVarighet() { return varighet; }

    
    public void setBrukernavn(String ny) { brukernavn = ny;}
    
    public void setPassord (String ny) { passord = ny;}
    
    public void setDato(String ny) { dato = ny; }
    
    public void setKategori(String ny) { kategori = ny; }

    public void setOktnummer(int ny) { oktnummer = ny;  }

    public void setTekst(String ny) { tekst = ny; }

    public void setVarighet(int ny) { varighet = ny; }
    
}


