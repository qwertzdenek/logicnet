package pia.validators;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@RequestScoped
public class SamePasswordValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIInput passwordField = (UIInput) context.getViewRoot().findComponent("registerForm:password");
        if (passwordField == null)
            throw new ValidatorException(new FacesMessage("Unable to find password component."));
        String password = (String) passwordField.getValue();
        String confirmPassword = (String) value;
        if (!confirmPassword.equals(password)) {
            throw new ValidatorException(new FacesMessage("Passwords do not match!"));
        }
    }
}
