package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("variables")
@SessionScoped

public class TreningsOkt implements Serializable{
    private int oktnummer;
    private String dato;
    private int varighet;
    private String kategori;
    private String tekst;

    public TreningsOkt(int oktnr, String dato, int varighet, String kategori, String tekst){
        this.oktnummer = oktnr;
        this.dato = dato;
        this.varighet = varighet;
        this.kategori = kategori;
        this.tekst = tekst;
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


