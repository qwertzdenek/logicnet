package pia.cdi;

import pia.dao.AccountDao;
import pia.dao.JPADAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class UserSession {
    @Inject
    @JPADAO
    AccountDao ad;

    @Inject
    HttpServletRequest req;

    public String logout() {
        try {
            req.logout();
        } catch (ServletException e) {
            System.out.println("WAT?");
        }
        return "/welcome.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        if (req.getUserPrincipal() != null) {
            return ad.findOne(req.getUserPrincipal().getName()).getName();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "User session for "+getUsername();
    }
}