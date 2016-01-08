package pia.cdi;

import pia.ServiceResult;
import pia.beans.RegisterService;
import pia.rest.entities.AccountEntity;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;

@Named
@SessionScoped
public class RegisterBean implements Serializable {
    @EJB
    private RegisterService rs;

    private AccountEntity account;
    private Part picture;

    public AccountEntity getAccount() {
        return account;
    }

    @PostConstruct
    private void init() {
        account = new AccountEntity();
    }

    public Part getPicture() {
        return picture;
    }

    public void setPicture(Part picture) {
        this.picture = picture;
    }

    public void register(ActionEvent e) {
        System.out.println(account);

        ServiceResult res = rs.register(account, picture);
        if (!res.getSuccess()) {
            throw new AbortProcessingException(res.getMessage());
        }
    }

    public void action() {
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("registerForm", new FacesMessage(FacesMessage.SEVERITY_INFO,"You have been registered!","You have been registered!"));
        fc.getExternalContext().getFlash().setKeepMessages(true);
    }
}
