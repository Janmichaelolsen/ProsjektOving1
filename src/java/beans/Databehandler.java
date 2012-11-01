/* 
 Dette er kontrollklassen som jsf kommuniserer med
 */
package beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("behandler")
@SessionScoped

public class Databehandler implements Serializable{
    private TreningsOkt tempOkt = new TreningsOkt();
    private Okter okter = new Okter();
    private ArrayList kategorier = new ArrayList();
    private boolean nykat = false;
    private String tempKat;
    List<OktStatus> synkListe = Collections.synchronizedList(new ArrayList<OktStatus>());
    Date date = new Date();
    private List<OktStatus> alleOkter = Collections.synchronizedList(new ArrayList<OktStatus>());
    private List<OktStatus> sortert = Collections.synchronizedList(new ArrayList<OktStatus>());
    private ArrayList aar = new ArrayList();
    private ArrayList mnd = new ArrayList();
    private int valgtaar;
    private int valgtmnd;
    
    public Databehandler(){
        tempOkt.setDato(date);
    }
    
    //Returnerer true hvis data finnes i tabellen
    public synchronized boolean getDataFins(){
        return synkListe.size() > 0;
    }
    
    //Registrering
    public void regOkt(){
        TreningsOkt nyokt = new TreningsOkt(tempOkt.getDato(), tempOkt.getVarighet(), 
                                            tempOkt.getKategori(), tempOkt.getTekst());
        nyokt.setOktnummer(getSisteOktnr());
        okter.regNyOkt(nyokt);
        synkListe.add(new OktStatus(nyokt));
        alleOkter.add(new OktStatus(nyokt));
        tempOkt.nullstill();
        
        for(int i=0; i<synkListe.size(); i++){
            int oktaar = synkListe.get(i).getOkten().getDato().getYear();
            int oktmnd = synkListe.get(i).getOkten().getDato().getMonth();
            if(!aar.contains(oktaar)){
                aar.add(oktaar);
            }
            if(!mnd.contains(oktmnd + 1)){
                mnd.add(oktmnd + 1);
            }
            
        }
    }
    
    public void oppdatersort(){
        if(valgtaar!=0){
            synkListe.clear();
            for(int i=0; i<alleOkter.size(); i++){
                synkListe.add(alleOkter.get(i));
            }
            for(int i=0; i<synkListe.size(); i++){
                if(synkListe.get(i).getOkten().getDato().getYear() == valgtaar){
                    sortert.add(synkListe.get(i));
                }
            }
            synkListe.clear();
            for(int i=0; i<sortert.size(); i++){
                synkListe.add(sortert.get(i)); 
            }
            sortert.clear();
        }
        
        
        if(valgtmnd!=0){
            for(int i=0; i<alleOkter.size(); i++){
                if(alleOkter.get(i).getOkten().getDato().getMonth()+1 == valgtmnd){
                    sortert.add(alleOkter.get(i));
                }
            }
            synkListe.clear();
            for(int i=0; i<sortert.size(); i++){
                synkListe.add(sortert.get(i));
            }
            sortert.clear();
        }
        
        
        if(valgtmnd == 0 && valgtaar == 0){
            synkListe.clear();
            for(int i=0; i<alleOkter.size(); i++){
                synkListe.add(alleOkter.get(i));
            }
        }
        
         if(valgtmnd != 0 && valgtaar != 0){
             for(int i=0; i<alleOkter.size(); i++){
                if(alleOkter.get(i).getOkten().getDato().getMonth()+1 == valgtmnd && alleOkter.get(i).getOkten().getDato().getYear() == valgtaar){
                    sortert.add(alleOkter.get(i));
                }
            }
            synkListe.clear();
            for(int i=0; i<sortert.size(); i++){
                synkListe.add(sortert.get(i));
            }
            sortert.clear();
        }
    }
    
    //Sletting
    public void oppdater(){
        int indeks = synkListe.size() - 1;
        while (indeks >= 0) {
            OktStatus os = synkListe.get(indeks);
            if (os.getSkalslettes()) {
                okter.fjernOkt(os.getOkten()); // sletter i problemdomeneobjekt
                synkListe.remove(indeks);  // sletter i presentasjonsobjektet
                }
                indeks--;
        }
        
    }
    
    //Henter ut øktnummeret som er det neste etter tabellens siste.
    public int getSisteOktnr(){ 
        if(synkListe.isEmpty()){
            return 1;
        }
        return synkListe.get(synkListe.size()-1).getOkten().getOktnummer()+1; 
 }  
    
    public ArrayList getListe(){ return okter.getListe(); }
    public Okter getOkter() { return okter; }
    
    public synchronized List<OktStatus> getSynkListe(){ return synkListe; }
    public synchronized TreningsOkt getTempOkt(){ return tempOkt; }
    public synchronized void setTempOkt(TreningsOkt ny){ tempOkt = ny; }
    
    
    //Metoder for tillegg av kategori
    public void leggTilKategori(){
        if(!tempKat.equals("")){
            kategorier.add(tempKat);
            tempKat = "";
        }
        nykat = false;
    }
    
    public boolean getNykat(){ return nykat; }
    public String getTempKat(){ return tempKat; }
    public ArrayList getTillegsOkter(){ return kategorier; }
    public void setnykat(){ nykat = true; }
    public void setTempKat(String ny){ tempKat = ny; }
   
    public int getValgtaar() { return valgtaar; }
    public int getValgtmnd() { return valgtmnd; }
    public void setValgtaar(int valgtaar) { this.valgtaar = valgtaar; }
    public void setValgtmnd(int valgtmnd) { this.valgtmnd = valgtmnd; }
    public ArrayList getAar() { return aar; }
    public ArrayList getMnd() { return mnd; }
    public void setAar(ArrayList aar) { this.aar = aar; }
    public void setMnd(ArrayList mnd) { this.mnd = mnd; }  
    public List<OktStatus> getAlleOkter() { return alleOkter; }
    public void setAlleOkter(List<OktStatus> alleOkter) { this.alleOkter = alleOkter; }
}
