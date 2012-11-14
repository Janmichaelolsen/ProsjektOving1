package beans;

public class Bruker {
  private String brukernavn = "";
  private String passord = "";
  private String epost = "";

  public Bruker() {
  }

  public Bruker(String brukernavn, String passord, String epost) {
    this.brukernavn = brukernavn;
    this.passord = passord;
    this.epost = epost;
  }

  public void nullstillData() {
    this.brukernavn = "";
    this.passord = "";
    this.epost = "";
  }

  public String getBrukernavn() {
    return brukernavn;
  }

  public void setBrukernavn(String nyttBrukernavn) {
    System.out.println("Brukernavn lest: " + nyttBrukernavn);
    brukernavn = nyttBrukernavn;
  }

  public String getPassord() {
    return passord;
  }

  public void setPassord(String nyttPassord) {
    System.out.println("Passord lest: " + nyttPassord);
    passord = nyttPassord;
  }

  public String getEpost() {
    return epost;
  }

  public void setEpost(String nyEpost) {
    epost = nyEpost;
  }

  public String toString() {
    return brukernavn + " " + passord + " " + epost;
  }
}