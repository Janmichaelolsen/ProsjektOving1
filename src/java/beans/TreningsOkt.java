package beans;

public class TreningsOkt{
    private int oktnummer;
    private String dato;
    private String varighet;
    private String kategori;
    private String tekst;

    public TreningsOkt(String dato, String varighet, String kategori, String tekst){
        this.dato = dato;
        this.varighet = varighet;
        this.kategori = kategori;
        this.tekst = tekst;
    }
    
    public TreningsOkt(){
        nullstill();
    }
    
    public final synchronized void nullstill(){
        oktnummer = 0;
        varighet = null;
        kategori = null;
        tekst = null;
    }
    
    public synchronized int getOktnummer() { return oktnummer; }
    
    public synchronized String getDato() { return dato; }

    public synchronized String getKategori() { return kategori; }

    public synchronized String getTekst() { return tekst; }

    public synchronized String getVarighet() { return varighet; }

    
    public synchronized void setDato(String ny) { dato = ny; }
    
    public synchronized void setKategori(String ny) { kategori = ny; }

    public synchronized void setOktnummer(int ny) { oktnummer = ny;  }

    public synchronized void setTekst(String ny) { tekst = ny; }

    public synchronized void setVarighet(String ny) { varighet = ny; }
    
}


