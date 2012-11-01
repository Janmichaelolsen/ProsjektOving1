package beans;

import java.util.Locale;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named("localeChanger")
@Dependent
public class LocaleChanger {

private FacesContext context = FacesContext.getCurrentInstance();
private Locale spraak = context.getViewRoot().getLocale();

    public Locale getLocale() {
        return spraak;
    }

    public String getLanguage() {
        return spraak.getLanguage();
    }
    
    public void setNorwegian(){
        context.getViewRoot().setLocale(new Locale("no"));
    }
    
    public void setEnglish(){
        context.getViewRoot().setLocale(new Locale("en"));
    }
}
