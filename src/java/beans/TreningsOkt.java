package beans;
import java.util.Date;

public class TreningsOkt{
    private int oktnummer;
    private Date dato;
    private Integer varighet;
    private String kategori;
    private String tekst;

    //To konstruktører avhengig av inndataene. Ingen data gitt resulterer i en "null"-økt.
    public TreningsOkt(Date dato, int varighet, String kategori, String tekst){
        this.dato = dato;
        this.varighet = varighet;
        this.kategori = kategori;
        this.tekst = tekst;
    }
    
    public TreningsOkt(){
        nullstill();
    }
    
    public TreningsOkt(int oktnr, Date dato, int varighet, String kategori, String tekst){
        this.oktnummer = oktnr;
        this.dato = dato;
        this.varighet = varighet;
        this.kategori = kategori;
        this.tekst = tekst;
    }
    
    //Metode for å nullstille en økt
    public final synchronized void nullstill(){
        oktnummer = 0;
        varighet = 0;
        kategori = null;
        tekst = null;
    }
    
    //Get og set metoder
    public synchronized Integer getOktnummer() { return oktnummer; }
    public synchronized Date getDato() { return dato; }
    public synchronized String getKategori() { return kategori; }
    public synchronized String getTekst() { return tekst; }
    public synchronized Integer getVarighet() { return varighet; }
    
    public synchronized void setDato(Date ny) { dato = ny; }
    public synchronized void setKategori(String ny) { kategori = ny; }
    public synchronized void setOktnummer(Integer ny) { oktnummer = ny;  }
    public synchronized void setTekst(String ny) { tekst = ny; }
    public synchronized void setVarighet(Integer ny) { varighet = ny; }
    
}


