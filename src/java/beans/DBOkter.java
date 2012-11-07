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

//    public void lesInn() throws Exception{
//        List<OktStatus> dbliste = Collections.synchronizedList(new ArrayList<OktStatus>());
//        Class.forName(dbdriver);  // laster inn driverklassen
//        Connection forbindelse = DriverManager.getConnection(dbnavn);
//        Statement setning = forbindelse.createStatement();
//        ResultSet res = setning.executeQuery("select * from WAPLJ_TRENING");
//        while (res.next()) {
//            Date dato = res.getDate("dato");
//            String kategori = res.getString("kategori");
//            tekst = res.getString("tekst");
//            int varighet = res.getInt("varighet");
//            System.out.println(dato + kategori + tekst + varighet);
//            TreningsOkt nyokt = new TreningsOkt(dato, varighet, kategori, tekst);
//            OktStatus stat = new OktStatus(nyokt);
//            dbliste.add(stat);
//            
//            
//        }
//        res.close();
//        setning.close();
//        forbindelse.close();
//    }
    
    public List<OktStatus> lesInn() throws Exception{
        List<OktStatus> dbliste = Collections.synchronizedList(new ArrayList<OktStatus>());
        Class.forName("org.apache.derby.jdbc.ClientDriver");  // laster inn driverklassen
        Connection forbindelse = DriverManager.getConnection("jdbc:derby://localhost:1527/waplj_prosjekt;user=waplj;password=waplj");
        Statement setning = forbindelse.createStatement();
        ResultSet res = setning.executeQuery("select * from WAPLJ.TRENING");
        while (res.next()) {
            Date dato = res.getDate("dato");
            String kategori = res.getString("kategorinavn");
            String tekst = res.getString("tekst");
            int varighet = res.getInt("varighet");
            TreningsOkt nyokt = new TreningsOkt(dato, varighet, kategori, tekst);
            OktStatus stat = new OktStatus(nyokt);
            dbliste.add(stat);
            
            
        }
        res.close();
        setning.close();
        forbindelse.close();
        return dbliste;
    }
}
