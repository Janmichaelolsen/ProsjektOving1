package beans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.sql.*;

@Named("list")
@SessionScoped
public class Okter implements Serializable {

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

    public double getSnittVarighet() {
        double sum = 0.0;
        for (TreningsOkt okt : liste) {
            sum += okt.getVarighet();
        }
        return sum / liste.size();
    }
}

class DatabaseKontakt {

    public static void main(String[] args) throws Exception {
        String databasedriver = "org.apache.derby.jdbc.ClientDriver";
        Class.forName(databasedriver);  // laster inn driverklassen
        String databasenavn =
                "jdbc:derby://localhost:1527/waplj_prosjekt;user=waplj;password=waplj";
        Connection forbindelse = DriverManager.getConnection(databasenavn);
    }
}
