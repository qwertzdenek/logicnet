package pia.validators;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Named
@RequestScoped
public class CaptchaValidator implements Validator{
    String charset = java.nio.charset.StandardCharsets.UTF_8.name();
    String url = "https://www.google.com/recaptcha/api/siteverify";

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        HttpURLConnection conn;
        InputStream is;

        HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        String response = req.getParameter("g-recaptcha-response");

        if (response == null) {
            throw new ValidatorException(new FacesMessage("Missing captcha response."));
        }

        try {
            String secret = "6Lf6EBUTAAAAAGmwcbkaqNKuo5Jhw_s7DkBwdh21";
            String query = String.format("secret=%s&response=%s",
                    URLEncoder.encode(secret, charset),
                    URLEncoder.encode(response, charset));

            conn = (HttpURLConnection) new URL(url + "?" + query).openConnection();
            conn.setRequestMethod("GET");

            is = conn.getInputStream();
        } catch (IOException e) {
            throw new ValidatorException(new FacesMessage("Error establishing connection to Google."));
        }

        JsonReader parser = Json.createReader(is);
        JsonObject obj = parser.readObject();
        Boolean success = obj.getBoolean("success");
        parser.close();

        if (!success) {
            throw new ValidatorException(new FacesMessage("Captcha not verified correctly."));
        }
    }
}
