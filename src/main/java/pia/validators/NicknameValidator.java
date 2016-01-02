package pia.validators;

import pia.beans.AccountService;
import pia.data.Account;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class NicknameValidator implements Validator {
    @Inject
    AccountService ac;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Account account = ac.getAccount((String) value);
        if (account != null) {
            throw new ValidatorException(new FacesMessage("This nickname is already used!"));
        }
    }
}
