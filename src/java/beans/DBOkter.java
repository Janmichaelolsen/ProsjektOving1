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

public class DBOkter {

    private String dbdriver = "org.apache.derby.jdbc.ClientDriver";
    private String dbnavn = "jdbc:derby://localhost:1527/persondata;user=vprg;password=vprg";

    public TreningsOkt lesInn() throws Exception{
        TreningsOkt okt = new TreningsOkt();
        Class.forName(dbdriver);  // laster inn driverklassen
        Connection forbindelse = DriverManager.getConnection(dbnavn);
        Statement setning = forbindelse.createStatement();
        ResultSet res = setning.executeQuery("select * from trening");
        while (res.next()) {
            Date dato = res.getDate("dato");
            String kategori = res.getString("kategori");
            String tekst = res.getString("tekst");
            int varighet = res.getInt("varighet");
            TreningsOkt nyokt = new TreningsOkt(dato, varighet, kategori, tekst);
            okt = nyokt;
            
        }
        res.close();
        setning.close();
        forbindelse.close();
        return okt;
    }
}
