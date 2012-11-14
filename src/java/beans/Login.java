package beans;

import java.sql.*;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

@Named("login")
@RequestScoped
public class Login {

    @Resource(name = "jdbc/waplj_prosjektressurs")
    DataSource ds;
    private Bruker enBruker = new Bruker();
    private Connection forbindelse;

    public Login() {
    }

    public Bruker getEnBruker() {
        return enBruker;
    }

    public void setEnBruker(Bruker ny) {
        enBruker = ny;
    }

    public boolean loggInn() {
        ÅpneForbindelse();
        PreparedStatement setning = null;
        ResultSet res = null;
        boolean returverdi = false;
        try {
            String brukernavn = enBruker.getBrukernavn();
            String passord = enBruker.getPassord();
            String sql = "select * from bruker where bruker = ? and passord = ?;";
            System.out.println("SQL: " + sql);
            setning = forbindelse.prepareStatement(sql);
            setning.setString(1, brukernavn);
            setning.setString(2, passord);

            setning.executeQuery();

            if (res.next()) {
                returverdi = true;
            }
        } catch (SQLException e) {
            System.out.println("Feil ved innlogging: " + e);
        } finally {
            Opprydder.lukkResSet(res);
            Opprydder.lukkSetning(setning);
            Opprydder.lukkForbindelse(forbindelse);
        }
        return returverdi;
    }

    private void ÅpneForbindelse() {
        try {
            if (ds == null) {
                throw new SQLException("Ingen datasource");
            }
            forbindelse = ds.getConnection();
            System.out.println("Tilkopling via datasource vellykket");
        } catch (Exception e) {
            System.out.println("Feil ved databasetilkopling: " + e);
        }
    }
}