/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Sigve
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBOkter {

    private String dbdriver = "org.apache.derby.jdbc.ClientDriver";
    private String dbnavn = "jdbc:derby://localhost:1527/waplj_prosjekt;user=waplj;password=waplj";
    Connection forbindelse;

    private void åpneForbindelse() {
        try {
            forbindelse = DriverManager.getConnection(dbnavn);
        } catch (SQLException e) {
            Opprydder.skrivMelding(e, "Konstruktøren");
            Opprydder.lukkForbindelse(forbindelse);
        }
    }

    private void lukkForbindelse() {
        System.out.println("Lukker databaseforbindelsen");
        Opprydder.lukkForbindelse(forbindelse);
    }

    public ArrayList<TreningsOkt> lesInn() {
        ArrayList<TreningsOkt> dbliste = new ArrayList<TreningsOkt>();
        try {
            Class.forName(dbdriver);  // laster inn driverklassen
            forbindelse = DriverManager.getConnection(dbnavn);
            Statement setning = forbindelse.createStatement();
            ResultSet res = setning.executeQuery("select * from trening");
            while (res.next()) {
                int oktnr = res.getInt("Oktnr");
                Date dato = res.getDate("Dato");
                String kategori = res.getString("Kategorinavn");
                String tekst = res.getString("Tekst");
                int varighet = res.getInt("Varighet");
                TreningsOkt nyokt = new TreningsOkt(oktnr, dato, varighet, kategori, tekst);
                dbliste.add(nyokt);
            }
            System.out.println("Henter");
            res.close();
            setning.close();
            forbindelse.close();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException q) {
            System.out.println(q);
        }
        return dbliste;
    }

    public void SkrivTil(TreningsOkt okt) {
        PreparedStatement regnyokt = null;
        åpneForbindelse();
        try {
            Class.forName(dbdriver);  // laster inn driverklassen
            forbindelse = DriverManager.getConnection(dbnavn);
            forbindelse.setAutoCommit(false);
            regnyokt = forbindelse.prepareStatement("insert into TRENING(DATO, VARIGHET, KATEGORINAVN, TEKST, BRUKERNAVN) values(?, ?, ?, ?, ?)");
            regnyokt.setDate(1, new java.sql.Date(okt.getDato().getTime()));
            regnyokt.setInt(2, okt.getVarighet());
            regnyokt.setString(3, okt.getKategori());
            regnyokt.setString(4, okt.getTekst());
            regnyokt.setString(5, "anne");
            System.out.println("Registrerer");
            regnyokt.executeUpdate();

            forbindelse.commit();
        } catch (SQLException e) {
            System.out.println(e);
            Opprydder.rullTilbake(forbindelse);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(regnyokt);
        }
        lukkForbindelse();
    }
    
    public void Endre(TreningsOkt okt) {
        PreparedStatement endre = null;
        åpneForbindelse();
        try {
            Class.forName(dbdriver);
            forbindelse = DriverManager.getConnection(dbnavn);
            forbindelse.setAutoCommit(false);
            endre = forbindelse.prepareStatement("update trening set dato=?, varighet=?, kategorinavn=?, tekst=? where oktnr=?");
            endre.setDate(1, new java.sql.Date(okt.getDato().getTime()));
            endre.setInt(2, okt.getVarighet());
            endre.setString(3, okt.getKategori());
            endre.setString(4, okt.getTekst());
            endre.setInt(5, okt.getOktnummer());
            
            endre.executeUpdate();

            forbindelse.commit();
        } catch (SQLException e) {
            System.out.println(e);
            Opprydder.rullTilbake(forbindelse);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(endre);
        }
        lukkForbindelse();
    }
    
    public void Slette(TreningsOkt okt) {
        PreparedStatement endre = null;
        åpneForbindelse();
        try {
            Class.forName(dbdriver);
            forbindelse = DriverManager.getConnection(dbnavn);
            forbindelse.setAutoCommit(false);
            endre = forbindelse.prepareStatement("delete from trening where oktnr=?");
            endre.setInt(1, okt.getOktnummer());
            
            endre.executeUpdate();
            System.out.println("Sletter");
            forbindelse.commit();
        } catch (SQLException e) {
            System.out.println(e);
            Opprydder.rullTilbake(forbindelse);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(endre);
        }
        lukkForbindelse();
    }
}
