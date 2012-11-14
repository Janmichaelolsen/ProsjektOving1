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
import javax.faces.context.FacesContext;

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
        Opprydder.lukkForbindelse(forbindelse);
    }

    public ArrayList<TreningsOkt> lesInn() {
        ArrayList<TreningsOkt> dbliste = new ArrayList<TreningsOkt>();
        try {
            Class.forName(dbdriver);  // laster inn driverklassen
            forbindelse = DriverManager.getConnection(dbnavn);
            Statement setning = forbindelse.createStatement();
            String bruker = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            System.out.println(bruker);
            ResultSet res = setning.executeQuery("select * from trening where brukernavn = '" + bruker + "'");
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

    public boolean SkrivTil(TreningsOkt okt) {
        PreparedStatement regnyokt = null;
        åpneForbindelse();
        boolean returverdi = true;
        try {
            Class.forName(dbdriver);  // laster inn driverklassen
            forbindelse = DriverManager.getConnection(dbnavn);
            forbindelse.setAutoCommit(false);
            regnyokt = forbindelse.prepareStatement("insert into TRENING(OKTNR, DATO, VARIGHET, KATEGORINAVN, TEKST, BRUKERNAVN) values(?, ?, ?, ?, ?, ?)");
            regnyokt.setInt(1, okt.getOktnummer());
            regnyokt.setDate(2, new java.sql.Date(okt.getDato().getTime()));
            regnyokt.setInt(3, okt.getVarighet());
            regnyokt.setString(4, okt.getKategori());
            regnyokt.setString(5, okt.getTekst());
            regnyokt.setString(6, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            System.out.println("Registrerer");
            regnyokt.executeUpdate();

            forbindelse.commit();
        } catch (SQLException e) {
            returverdi = false;
            System.out.println(e);
            Opprydder.rullTilbake(forbindelse);
        } catch (ClassNotFoundException e) {
            returverdi = false;
            System.out.println(e);
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(regnyokt);
        }
        lukkForbindelse();
        return returverdi;
    }
    
    public void Endre(TreningsOkt okt) {
        PreparedStatement endre = null;
        åpneForbindelse();
        try {
            Class.forName(dbdriver);
            forbindelse = DriverManager.getConnection(dbnavn);
            forbindelse.setAutoCommit(false);
            endre = forbindelse.prepareStatement("update trening set dato=?, varighet=?, kategorinavn=?, tekst=? where oktnr=? and brukernavn=?");
            endre.setDate(1, new java.sql.Date(okt.getDato().getTime()));
            endre.setInt(2, okt.getVarighet());
            endre.setString(3, okt.getKategori());
            endre.setString(4, okt.getTekst());
            endre.setInt(5, okt.getOktnummer());
            endre.setString(6, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            
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
            endre = forbindelse.prepareStatement("delete from trening where oktnr=? and brukernavn=?");
            endre.setInt(1, okt.getOktnummer());
            endre.setString(2, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            
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
    
    public void EndrePassord(String nyttpassord) {
        PreparedStatement endre = null;
        åpneForbindelse();
        try {
            Class.forName(dbdriver);
            forbindelse = DriverManager.getConnection(dbnavn);
            forbindelse.setAutoCommit(false);
            endre = forbindelse.prepareStatement("update bruker set passord=? where brukernavn = ?");
            endre.setString(1, nyttpassord);
            endre.setString(2, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
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
    
    public ArrayList lesInnKat() {
        ArrayList katliste = new ArrayList();
        try {
            Class.forName(dbdriver);  // laster inn driverklassen
            forbindelse = DriverManager.getConnection(dbnavn);
            Statement setning = forbindelse.createStatement();
            String bruker = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            ResultSet res = setning.executeQuery("select * from tilleggskat where brukernavn = '"+bruker+"'");
            while (res.next()) {
                String kat = res.getString("tilleggkat");
                katliste.add(kat);
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
        return katliste;
    }
    
    public void Leggtilkat(String kat) {
        PreparedStatement regnykat = null;
        åpneForbindelse();
        try {
            Class.forName(dbdriver);  // laster inn driverklassen
            forbindelse = DriverManager.getConnection(dbnavn);
            forbindelse.setAutoCommit(false);
            regnykat = forbindelse.prepareStatement("insert into tilleggskat(tilleggkat, brukernavn) values(?, ?)");
            regnykat.setString(1, kat);
            regnykat.setString(2, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            regnykat.executeUpdate();

            forbindelse.commit();
        } catch (SQLException e) {
            System.out.println(e);
            Opprydder.rullTilbake(forbindelse);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(regnykat);
        }
        lukkForbindelse();
    }
}
