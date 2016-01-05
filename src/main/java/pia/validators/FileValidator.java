package pia.validators;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Named
@RequestScoped
public class FileValidator implements Validator {
    Set<String> allowedTypes = new LinkedHashSet<>();

    public FileValidator() {
        allowedTypes.add("image/png");
        allowedTypes.add("image/jpeg");
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        List<FacesMessage> msgs = new ArrayList<>();
        Part file = (Part) value;
        if (file == null) {
            msgs.add(new FacesMessage("file is required"));
        } else {
            if (file.getSize() > 2097152) {
                msgs.add(new FacesMessage("file too big (Max 2MB)"));
            }
            if (!allowedTypes.contains(file.getContentType())) {
                msgs.add(new FacesMessage("Please upload only PNG or JPEG"));
            }
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }
}
