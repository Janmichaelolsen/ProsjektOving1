package beans;

import java.util.ArrayList;

public class Okter {

    ArrayList<TreningsOkt> liste = new ArrayList<TreningsOkt>();
    DBOkter db = new DBOkter();

    public Okter() {
        liste = db.lesInn();
    }

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
}
