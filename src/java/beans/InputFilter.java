package beans;

import javax.faces.context.FacesContext;

public class InputFilter {
  private final String spesialtegn = "-_,.";
  private String passord1;
  private String passord2;
  private DBOkter db;

  public String sjekkPassord() {
      int antallspestegn = 0;
      int antallsiffer = 0;
      int antbokstaver = 0;
      int antalltegn = 0;
    if (passord1 == null) {
        return "Vennligst skriv inn et passord.";
    }
    if(!passord1.equals(passord2)){
        return "Passordene må være like.";
    }
    
    for (int i = 0; i < passord1.length(); i++) {
      char tegn = passord1.charAt(i);
      
      /* isLetterOrDigit() bruker tegnsettet som maskinen er satt opp med */
      if (!(Character.isLetterOrDigit(tegn) || spesialtegn.indexOf(tegn) >= 0)) {
          return "Passordet kan bare inneholde følgende spesialtegn: bindestrek, understrek, komma, punktum.";
      }
      if(Character.isLetter(tegn)) antbokstaver++;
      if(Character.isDigit(tegn)) antallsiffer++;
      if(spesialtegn.indexOf(tegn) >= 0) antallspestegn++;
      antalltegn++;
      
      
    }
    if(antallspestegn > 0 && antallsiffer > 0 && antallspestegn > 0 && antalltegn >= 6){
          db.EndrePassord(passord1);
          return "Passord endret!";
      }
    return "Noe gikk galt.";
  }
}