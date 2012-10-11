package beans;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Locale;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author Michael
 */
@Named(value = "localeChanger")
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
