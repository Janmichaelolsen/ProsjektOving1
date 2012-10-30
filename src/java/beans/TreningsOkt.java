package beans;

public class TreningsOkt{
    private int oktnummer;
    private String dato;
    private int varighet;
    private String kategori;
    private String tekst;
    
    private static int sisteOktnr = 0;

    public TreningsOkt(String dato, int varighet, String kategori, String tekst){
        this.dato = dato;
        this.varighet = varighet;
        this.kategori = kategori;
        this.tekst = tekst;
    }
    
    public TreningsOkt(){
        nullstill();
    }
    
    public static int lagnyoktnr(){
        sisteOktnr++;
        return sisteOktnr;
    }
    
    public final synchronized void nullstill(){
        oktnummer = 0;
        varighet = 0;
        kategori = null;
        tekst = null;
    }
    
    public synchronized int getOktnummer() { return oktnummer; }
    
    public synchronized String getDato() { return dato; }

    public synchronized String getKategori() { return kategori; }

    public synchronized String getTekst() { return tekst; }

    public synchronized int getVarighet() { return varighet; }

    
    public synchronized void setDato(String ny) { dato = ny; }
    
    public synchronized void setKategori(String ny) { kategori = ny; }

    public synchronized void setOktnummer(int ny) { oktnummer = ny;  }

    public synchronized void setTekst(String ny) { tekst = ny; }

    public synchronized void setVarighet(int ny) { varighet = ny; }
    
}


