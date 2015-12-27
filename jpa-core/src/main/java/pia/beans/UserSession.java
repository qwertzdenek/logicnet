package pia.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named(value = "userSession")
@RequestScoped
public class UserSession {
    @Inject
    HttpServletRequest req;

    public String logout() {
        System.out.println("LOGOUT");
        try {
            req.logout();
        } catch (ServletException e) {
            System.out.println("WAT?");
        }
        return "/faces/welcome.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return req.getUserPrincipal().getName();
    }

    @Override
    public String toString() {
        return "User session for "+getUsername();
    }
}
