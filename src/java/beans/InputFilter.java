package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("nypass")
@SessionScoped

public class InputFilter implements Serializable{
  private final String spesialtegn = "-_,.";
  private String passord1;
  private String passord2;
  private String resultat = "";
  private DBOkter db = new DBOkter();
  private boolean nyttpass = false;

  public void sjekkPassord() {
      int antallspestegn = 0;
      int antallsiffer = 0;
      int antbokstaver = 0;
      int antalltegn = 0;
    if (passord1.equals("")) {
        resultat = "Vennligst skriv inn et passord.";
        return;
    }
    if(!passord1.equals(passord2)){
        resultat = "Passordene må være like.";
        return;
    }
    
    for (int i = 0; i < passord1.length(); i++) {
      char tegn = passord1.charAt(i);
      
      /* isLetterOrDigit() bruker tegnsettet som maskinen er satt opp med */
      if (!(Character.isLetterOrDigit(tegn)) && !(spesialtegn.indexOf(tegn) >= 0)) {
          resultat = "Spesialtegn";
          return;
      }
      if(Character.isLetter(tegn)){
          antbokstaver++;
      }
      if(Character.isDigit(tegn)){
          antallsiffer++;
      }
      if(spesialtegn.indexOf(tegn) >= 0){
          antallspestegn++;
      }
      antalltegn++;
      
      
    }
    if(antallspestegn > 0 && antallsiffer > 0 && antallspestegn > 0 && antalltegn >= 6){
        try{  
        db.EndrePassord(passord1);
        resultat = "Passord endret!";
        } catch(Exception e){
            System.out.println(e);
            resultat="exception";
        }   
      }
    else{
        resultat = "For kort eller ikke nok bokstaver, tall eller spesialtegn";
    }
  }

    public boolean isNyttpass() {
        return nyttpass;
    }

    public void setNyttpass() {
        this.nyttpass = true;
    }

    public String getPassord1() {
        return passord1;
    }

    public String getPassord2() {
        return passord2;
    }

    public void setPassord1(String passord1) {
        this.passord1 = passord1;
    }

    public void setPassord2(String passord2) {
        this.passord2 = passord2;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getResultat() {
        return resultat;
    }
    
    
  
  
}