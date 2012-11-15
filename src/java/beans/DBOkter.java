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

import javax.annotation.Resource;
import javax.naming.Context;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBOkter {
    @Resource(name="jdbc/waplj_prosjekt")
    private DataSource ds;
    private Connection forbindelse;
    
    public DBOkter(){
        try{
            Context con = new InitialContext();
            ds = (DataSource) con.lookup("jdbc/waplj_prosjekt");
        } catch (NamingException e){
            System.out.println(e.getMessage());
        }
    }

    private void åpneForbindelse() {
        try {
            if(ds == null){
                throw new SQLException("Ingen forbindelse");
            }
            forbindelse = ds.getConnection();
            System.out.println("Forbindelse opprettet");
        } catch (SQLException e) {
            Opprydder.skrivMelding(e, "Konstruktøren");
        }
    }

    private void lukkForbindelse() {
        Opprydder.lukkForbindelse(forbindelse);
    }

    public ArrayList<TreningsOkt> lesInn() {
        ArrayList<TreningsOkt> dbliste = new ArrayList<TreningsOkt>();
        åpneForbindelse();
        try {
            Statement setning = forbindelse.createStatement();
            String bruker = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
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
        }
        return dbliste;
    }

    public boolean SkrivTil(TreningsOkt okt) {
        PreparedStatement regnyokt = null;
        åpneForbindelse();
        boolean returverdi = true;
        try {
        
            forbindelse.setAutoCommit(false);
            regnyokt = forbindelse.prepareStatement("insert into TRENING(OKTNR, DATO, VARIGHET, KATEGORINAVN, TEKST, BRUKERNAVN) values(?, ?, ?, ?, ?, ?)");
            regnyokt.setInt(1, okt.getOktnummer());
            regnyokt.setDate(2, new java.sql.Date(okt.getDato().getTime()));
            regnyokt.setInt(3, okt.getVarighet());
            regnyokt.setString(4, okt.getKategori());
            regnyokt.setString(5, okt.getTekst());
            regnyokt.setString(6, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            regnyokt.executeUpdate();

            forbindelse.commit();
        } catch (SQLException e) {
            returverdi = false;
            System.out.println(e);
            Opprydder.rullTilbake(forbindelse);
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
            åpneForbindelse();
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
            åpneForbindelse();
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
            åpneForbindelse();
            forbindelse.setAutoCommit(false);
            System.out.println("Passord" + nyttpassord);
            endre = forbindelse.prepareStatement("update bruker set passord=? where brukernavn = ?");
            endre.setString(1, nyttpassord);
            endre.setString(2, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            endre.executeUpdate();

            forbindelse.commit();
        } catch (SQLException e) {
            System.out.println(e);
            Opprydder.rullTilbake(forbindelse);
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(endre);
        }
        lukkForbindelse();
    }
    
    public ArrayList lesInnKat() {
        ArrayList katliste = new ArrayList();
        åpneForbindelse();
        try {
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
        }
        return katliste;
    }
    
    public void Leggtilkat(String kat) {
        PreparedStatement regnykat = null;
        åpneForbindelse();
        try {
            åpneForbindelse();
            forbindelse.setAutoCommit(false);
            regnykat = forbindelse.prepareStatement("insert into tilleggskat(tilleggkat, brukernavn) values(?, ?)");
            regnykat.setString(1, kat);
            regnykat.setString(2, FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            regnykat.executeUpdate();

            forbindelse.commit();
        } catch (SQLException e) {
            System.out.println(e);
            Opprydder.rullTilbake(forbindelse);
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(regnykat);
        }
        lukkForbindelse();
    }
}
