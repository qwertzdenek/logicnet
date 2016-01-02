package pia.cdi;

import pia.beans.AccountService;
import pia.data.Account;
import pia.util.DataManager;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Named
@SessionScoped
public class RegisterBean implements Serializable {
    @Inject
    AccountService rs;

    private String realName;
    private String nickname;
    private String password;
    private String birthdate;
    private Part picture;

    @Size(min = 8, message = "Name must be 8 characters long.")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Size(min = 4, message = "Nickname must be at least 4 characters long.")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Size(min = 8, message = "Minimum length is 8 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Part getPicture() {
        return picture;
    }

    public void setPicture(Part picture) {
        this.picture = picture;
    }

    public void register() {
        Account account = new Account();
        account.setId(nickname);
        account.setPassword(password);
        account.setName(realName);
        account.addRole("user");

        String fileName = DataManager.store(picture);

        if (fileName == null) {
            fileName = "generic.png";
        }

        account.setProfilePicture(fileName);

        if (birthdate != null) {
            java.util.Date parsedDate;
            try {
                 parsedDate = new SimpleDateFormat("dd/mm/yyyy").parse(birthdate);
            } catch (ParseException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot parse birthdate!", "You given "+birthdate);
                throw new ValidatorException(message);
            }
            account.setBirthday(new Date(parsedDate.getTime()));
        }

        rs.createRegistration(account);
    }
}
