package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("valider")
@SessionScoped
public class Validering implements Serializable {

    private final String spesialtegn = "-_,.";
    private String gammelt;
    private String passord1;
    private String passord2;
    private String resultat = "";
    private DBOkter db = new DBOkter();
    private boolean nyttpass = false;
    private String brukernavn;

    public boolean sjekkPassord(boolean nybruker) {
        int antallspestegn = 0;
        int antallsiffer = 0;
        int antbokstaver = 0;
        int antalltegn = 0;

        if (!nybruker) {
            if (db.hentPassord(brukernavn).equals(gammelt)) {
                resultat = "Feil gammelt passord.";
                return false;
            }
        }

        if (passord1.equals("")) {
            resultat = "Vennligst skriv inn et passord.";
            return false;
        }
        if (!passord1.equals(passord2)) {
            resultat = "Passordene må være like.";
            return false;
        }

        for (int i = 0; i < passord1.length(); i++) {
            char tegn = passord1.charAt(i);

            /* isLetterOrDigit() bruker tegnsettet som maskinen er satt opp med */
            if (!(Character.isLetterOrDigit(tegn)) && !(spesialtegn.indexOf(tegn) >= 0)) {
                resultat = "Spesialtegn";
                return false;
            }
            if (Character.isLetter(tegn)) {
                antbokstaver++;
            }
            if (Character.isDigit(tegn)) {
                antallsiffer++;
            }
            if (spesialtegn.indexOf(tegn) >= 0) {
                antallspestegn++;
            }
            antalltegn++;
        }
        if (antallspestegn > 0 && antallsiffer > 0 && antallspestegn > 0 && antalltegn >= 6) {
            return true;
        } else {
            resultat = "For kort eller ikke nok bokstaver, tall eller spesialtegn";
            return false;
        }
    }
    
    public void endrePass(){
        if(sjekkPassord(false)){
            try{
                db.EndrePassord(passord1);
            } catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public void lagBruker(){
        if(!db.sjekkDuplikat(brukernavn)){
           resultat = "Brukernavnet finnes fra før";
           return;
        }
        if(sjekkPassord(true)){
            if(db.leggtilBruker(brukernavn, passord1)){
                db.registrerRolle(brukernavn);
                resultat = "Registrert!";
            }
            else{
                resultat = "Noe gikk galt i databaseforbindelsen";
            }
        }
    }

    public boolean isNyttpass() {
        return nyttpass;
    }

    public void setNyttpasst() {
        this.nyttpass = true;
        resultat="";
    }

    public void setNyttpassf() {
        this.nyttpass = false;
    }

    public String getPassord1() {
        return passord1;
    }

    public String getPassord2() {
        return passord2;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;

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

    public String getGammelt() {
        return gammelt;
    }

    public void setGammelt(String gammelt) {
        this.gammelt = gammelt;
    } 
}