package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("valider")
@SessionScoped
public class Validering implements Serializable {

    private final String spesialtegn = "-_,.";
    private String gammelt;
    private String passord1;
    private String passord2;
    //boolean tabell: gammelfeil, passordtomt, passikkelik, spestegn, lengde/ant, brukernavnfinnes, dbfeil.
    private boolean[] feil = {false, false, false, false, false, false, false};
    private boolean riktig = false;
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
                feil[0] = true;
                return false;
            }
            else{
                feil[0] = false;
            }
        }

        if (passord1.equals("")) {
            feil[1] = true;
            return false;
        }
        else{
            feil[1] = false;
        }
        if (!passord1.equals(passord2)) {
            feil[2] = true;
            return false;
        }
        else{
            feil[2] = false;
        }

        for (int i = 0; i < passord1.length(); i++) {
            char tegn = passord1.charAt(i);

            if (!(Character.isLetterOrDigit(tegn)) && !(spesialtegn.indexOf(tegn) >= 0)) {
                feil[3] = true;
                return false;
            }
            else{
                feil[3] = false;
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
            feil[4] = false;
            return true;
        } else {
            feil[4] = true;
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
           feil[5] = true;
           return;
        }
        else{
            feil[5] = false;
        }
        if(sjekkPassord(true)){
            if(db.leggtilBruker(brukernavn, passord1)){
                db.registrerRolle(brukernavn);
                riktig = true;
                feil[6] = false;
            }
            else{
                feil[6] = true;
            }
        }
    }
    
    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(false);
        httpSession.invalidate();
        return "../index.xhtml?faces-redirect=true";
    }

    public boolean isNyttpass() {
        return nyttpass;
    }

    public void setNyttpasst() {
        this.nyttpass = true;
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

    public String getGammelt() {
        return gammelt;
    }

    public void setGammelt(String gammelt) {
        this.gammelt = gammelt;
    } 
    
    public boolean getFeil0(){
        return feil[0];
    }
    
    public boolean getFeil1(){
        return feil[1];
    }
    
    public boolean getFeil2(){
        return feil[2];
    }
    
    public boolean getFeil3(){
        return feil[3];
    }
    
    public boolean getFeil4(){
        return feil[4];
    }
    
    public boolean getFeil5(){
        return feil[5];
    }
    
    public boolean getFeil6(){
        return feil[6];
    }
    
    public boolean getRiktig(){
        return riktig;
    }
    
}