package beans;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
    
    public Databehandler(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        tempOkt.setDato(sdf.format(date));
    }
    
    public synchronized boolean getDataFins(){
        return synkListe.size() > 0;
    }
    
    public void regOkt(){
        //Registrering
        TreningsOkt nyokt = new TreningsOkt(tempOkt.getDato(), tempOkt.getVarighet(), 
                                            tempOkt.getKategori(), tempOkt.getTekst());
        nyokt.setOktnummer(getSisteOktnr());
        okter.regNyOkt(nyokt);
        synkListe.add(new OktStatus(nyokt));
        tempOkt.nullstill();
    }
    
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
    
    public int getSisteOktnr(){ 
        if(synkListe.isEmpty()){
            return 1;
        }
        return synkListe.get(synkListe.size()-1).getOkten().getOktnummer()+1; 
 }  
    public void leggTilKategori(){
        if(!tempKat.equals("")){
            kategorier.add(tempKat);
            tempKat = "";
        }
        nykat = false;
    }
    
    public void setnykat(){ 
        if(!nykat){
            nykat=true;
        }
    }
    public boolean getNykat(){ return nykat; }
    public String getTempKat(){ return tempKat; }
    public void setTempKat(String ny){ tempKat = ny; }
    public ArrayList getListe(){ return okter.getListe(); }
    public Okter getOkter() { return okter;}
    public synchronized List<OktStatus> getSynkListe(){ return synkListe; }
    public synchronized TreningsOkt getTempOkt(){ return tempOkt; }
    public ArrayList getTillegsOkter(){ return kategorier; }
    public synchronized void setTempOkt(TreningsOkt ny){ tempOkt = ny; }
}
