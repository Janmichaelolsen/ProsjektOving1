package beans;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Okter {

    ArrayList<TreningsOkt> liste = new ArrayList<TreningsOkt>();

    public void regNyOkt(TreningsOkt okt) {
        if (okt != null) {
            liste.add(okt);
        }
    }

    public void fjernOkt(TreningsOkt okt) {
        liste.remove(okt);
    }

    public ArrayList<TreningsOkt> getListe() {
        return liste;
    }
    
    public int getAntallokter() {
        return liste.size();
    }
    
    public String getSnittVarighet(){
        double sum = 0.0;
        for(TreningsOkt okt : liste){
            sum += okt.getVarighet();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(sum/liste.size());
    }
}
