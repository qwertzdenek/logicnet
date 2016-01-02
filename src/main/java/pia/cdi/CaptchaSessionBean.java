package pia.cdi;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CaptchaSessionBean implements Serializable {
    private String sessionKeyName;

    public String getSessionKeyName() {
        return sessionKeyName;
    }

    public void setSessionKeyName(String sessionKeyName) {
        this.sessionKeyName = sessionKeyName;
    }
}
