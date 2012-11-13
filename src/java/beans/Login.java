package beans;
import java.sql.*;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named("login")
@RequestScoped
public class Login{
    @Resource(name="jdbc/waplj_prosjekt") DataSource ds;
   private Bruker enBruker = new Bruker();
   private Connection forbindelse;
   
   public Login(){
   }
   
   public Bruker getEnBruker(){
       return enBruker;
   }
   public void setEnBruker(Bruker ny){
       enBruker = ny;
   }
   public String loggInn(){
       åpneForbindelse();
       Statement setning = null;
       ResultSet res = null;
       String returverdi = "Feil i login";
       try{
           String brukernavn = enBruker.getUsername();
           String passord = enBruker.getPassword();
           String sql = "select * from bruker where bruker = '" + brukernavn 
                   + "' and passord = '" + passord + "'";
           System.out.println("SQL: " + sql);
           
           setning = forbindelse.createStatement();
           res = setning.executeQuery(sql);
           if(res.next()){
              returverdi = "Innlogging ok"; 
           }
       } catch (SQLException e){
           System.out.println("Feil ved innlogging: " + e);
       } finally{
           Opprydder.lukkResSet(res);
           Opprydder.lukkSetning(setning);
           Opprydder.lukkForbindelse(forbindelse);
       }
       return returverdi;
   }
   
   private void åpneForbindelse(){
       String databasedriver;
       try{
           if(ds == null){
               throw new SQLException("Ingen datasource");
           }
           forbindelse = ds.getConnection();
           System.out.println("Tilkopling via datasource vellykket");
       } catch(Exception e){
           System.out.println("Feil ved databasetilkopling: " + e);
       }
   }
}