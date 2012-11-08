/* 
 Dette er kontrollklassen som jsf kommuniserer med
 */
package beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("behandler")
@SessionScoped
public class Databehandler implements Serializable {

    Date date = new Date();
    DBOkter db = new DBOkter();
    private Okter okter = new Okter();
    
    private ArrayList kategorier = new ArrayList();
    private List<OktStatus> synkListe = hentfraDB();
    private List<OktStatus> alleOkter = hentfraDB();
    private TreningsOkt tempOkt = new TreningsOkt();
    private ArrayList aar = new ArrayList();
    private ArrayList mnd = new ArrayList();
    private boolean nykat = false;
    private boolean ascending = true;
    private String tempKat;
    private int valgtaar;
    private int valgtmnd;
    
    
    public Databehandler() {
        tempOkt.setDato(date);
        filtrer();
    }
    
    //Returnerer true hvis data finnes i tabellen
    public synchronized boolean getDataFins() {
        return alleOkter.size() > 0;
    }

    //Registrering
    public void regOkt() {
        TreningsOkt nyokt = new TreningsOkt(tempOkt.getDato(), tempOkt.getVarighet(),
                tempOkt.getKategori(), tempOkt.getTekst());
        skrivtilDB(nyokt);
        nyokt.setOktnummer(getSisteOktnr());
        okter.regNyOkt(nyokt);
        OktStatus nystat = new OktStatus(nyokt);
        synkListe.add(nystat);
        alleOkter.add(nystat);
        tempOkt.nullstill();
        leggtilFilt();
    }

    //Sletting
    public void slett() {
        slettDB();
        int indeks = synkListe.size() - 1;
        while (indeks >= 0) {
            OktStatus os = synkListe.get(indeks);
            if (os.getSkalslettes()) {
                okter.fjernOkt(os.getOkten()); // sletter i problemdomeneobjekt
                synkListe.remove(indeks);  // sletter i presentasjonsobjektet
                alleOkter.remove(indeks);
            }
            indeks--;
        }
    }

    //Henter ut øktnummeret som er det neste etter tabellens siste.
    public int getSisteOktnr() {
        if (synkListe.isEmpty()) {
            return 1;
        }
        return synkListe.get(synkListe.size() - 1).getOkten().getOktnummer() + 1;
    }

    public int getAntallokter() {
        return synkListe.size();
    }

    public String getSnittVarighet() {
        double sum = 0.0;
        for (OktStatus okt : synkListe) {
            sum += okt.getOkten().getVarighet();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(sum / synkListe.size());
    }
    
    //Metoder for tillegg av kategori
    public void leggTilKategori() {
        if (!tempKat.equals("")) {
            kategorier.add(tempKat);
            tempKat = "";
        }
        nykat = false;
    }

    //Legger til nye år i listen til filtreringen.
    public void leggtilFilt() {
        for (int i = 0; i < synkListe.size(); i++) {
            int oktaar = synkListe.get(i).getOkten().getDato().getYear() + 1900;
            int oktmnd = synkListe.get(i).getOkten().getDato().getMonth();
            if (!aar.contains(oktaar)) {
                aar.add(oktaar);
            }
            if (!mnd.contains(oktmnd + 1)) {
                mnd.add(oktmnd + 1);
            }
        }
        Collections.sort(aar);
        Collections.sort(mnd);
    }

    //Metoden for å filtrere og legge til aktuelle økter i listen.
    public void filtrer() {
        leggtilFilt();
        synkListe.clear();
        for (int i = 0; i < alleOkter.size(); i++) {
            int oktaar = alleOkter.get(i).getOkten().getDato().getYear() + 1900;
            int oktmnd = alleOkter.get(i).getOkten().getDato().getMonth() + 1;
            if (valgtaar != 0 && valgtmnd == 0) {
                if (oktaar == valgtaar) {
                    synkListe.add(alleOkter.get(i));
                }
            }
            if (valgtmnd != 0 && valgtaar == 0) {
                if (oktmnd == valgtmnd) {
                    synkListe.add(alleOkter.get(i));
                }
            }
            if (valgtmnd == 0 && valgtaar == 0) {
                synkListe.add(alleOkter.get(i));
            }
            if (valgtmnd != 0 && valgtaar != 0) {
                if (oktmnd == valgtmnd && oktaar == valgtaar) {
                    synkListe.add(alleOkter.get(i));
                }
            }
        }
    }
    public TimeZone getTimeZone(){
        TimeZone tz = TimeZone.getDefault();
        return tz;
    }

    public String sorterListeOktnr() {
        if (ascending) {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o1.getOkten().getOktnummer().compareTo(o2.getOkten().getOktnummer());
                }
            });
            ascending = false;
        } else {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o2.getOkten().getOktnummer().compareTo(o1.getOkten().getOktnummer());
                }
            });
            ascending = true;
        }
        filtrer();
        return null;
    }

    public String sorterListeDato(){
        if (ascending) {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o1.getOkten().getDato().compareTo(o2.getOkten().getDato());
                }
            });
            ascending = false;
        } else {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o2.getOkten().getDato().compareTo(o1.getOkten().getDato());
                }
            });
            ascending = true;
        }
        filtrer();
        return null;
    }
    
    public String sorterListeVarighet() {
        if (ascending) {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o1.getOkten().getVarighet().compareTo(o2.getOkten().getVarighet());
                }
            });
            ascending = false;
        } else {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o2.getOkten().getVarighet().compareTo(o1.getOkten().getVarighet());
                }
            });
            ascending = true;
        }
        filtrer();
        return null;
    }

    public String sorterListeKategori() {
        if (ascending) {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o1.getOkten().getDato().compareTo(o2.getOkten().getDato());
                }
            });
            ascending = false;
        } else {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o2.getOkten().getKategori().compareTo(o1.getOkten().getKategori());
                }
            });
            ascending = true;
        }
        filtrer();
        return null;
    }
    public String sorterListeTekst() {
        if (ascending) {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o1.getOkten().getTekst().compareTo(o2.getOkten().getTekst());
                }
            });
            ascending = false;
        } else {
            Collections.sort(alleOkter, new Comparator<OktStatus>() {
                @Override
                public int compare(OktStatus o1, OktStatus o2) {
                    return o2.getOkten().getKategori().compareTo(o1.getOkten().getKategori());
                }
            });
            ascending = true;
        }
        filtrer();
        return null;
    }
    
    public List<OktStatus> hentfraDB(){
        List<OktStatus> dbokter = Collections.synchronizedList(new ArrayList<OktStatus>());
        ArrayList<TreningsOkt> liste = okter.getListe();
        for(int i=0; i<liste.size(); i++){
            OktStatus nyokt = new OktStatus(liste.get(i));
            dbokter.add(nyokt);
        }
        return dbokter;
    }
    public void skrivtilDB(TreningsOkt okt){
        try{
        db.SkrivTil(okt);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void endreDB(TreningsOkt okt){
        try{
        db.Endre(okt);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void slettDB(){
        for(int i=0; i<alleOkter.size(); i++){
            if(alleOkter.get(i).getSkalslettes()){
                try{
                db.Slette(alleOkter.get(i).getOkten());
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
    }
    
    public ArrayList getListe() {
        return okter.getListe();
    }

    public Okter getOkter() {
        return okter;
    }

    public synchronized List<OktStatus> getSynkListe() {
        return synkListe;
    }

    public synchronized void setSynkListe(List<OktStatus> ny) {
        synkListe = ny;
    }

    public synchronized TreningsOkt getTempOkt() {
        return tempOkt;
    }

    public synchronized void setTempOkt(TreningsOkt ny) {
        tempOkt = ny;
    }
    
    public boolean getNykat() {
        return nykat;
    }

    public String getTempKat() {
        return tempKat;
    }

    public ArrayList getTillegsOkter() {
        return kategorier;
    }

    public void setnykat() {
        nykat = true;
    }

    public void setTempKat(String ny) {
        tempKat = ny;
    }

    public int getValgtaar() {
        return valgtaar;
    }

    public int getValgtmnd() {
        return valgtmnd;
    }

    public void setValgtaar(int valgtaar) {
        this.valgtaar = valgtaar;
    }

    public void setValgtmnd(int valgtmnd) {
        this.valgtmnd = valgtmnd;
    }

    public ArrayList getAar() {
        return aar;
    }

    public ArrayList getMnd() {
        return mnd;
    }

    public void setAar(ArrayList aar) {
        this.aar = aar;
    }

    public void setMnd(ArrayList mnd) {
        this.mnd = mnd;
    }

    public List<OktStatus> getAlleOkter() {
        return alleOkter;
    }
}
