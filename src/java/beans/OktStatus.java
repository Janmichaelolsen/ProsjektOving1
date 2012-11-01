package beans;

public class OktStatus {

private TreningsOkt okten;
private boolean editable;
private boolean skalslettes;


    public OktStatus(TreningsOkt okt){
        this.okten = okt;
        editable = false;
        skalslettes = false;
    }

    //Get og set metoder
    public void setOkten(TreningsOkt ny){ okten = ny; }
    public TreningsOkt getOkten(){ return okten; }

    public boolean getEditable(){ return editable; }
    public void setEditable(boolean ny) { editable = ny; }

    public boolean getSkalslettes(){ return skalslettes; }
    public void setSkalslettes(boolean ny) { skalslettes = ny; }
}
