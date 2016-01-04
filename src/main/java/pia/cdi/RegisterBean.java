package pia.cdi;

import pia.rest.AccountResource;
import pia.rest.entities.AccountEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.Serializable;

@Named
@SessionScoped
public class RegisterBean implements Serializable {
    @Inject
    AccountResource ar;

    AccountEntity entity;

    @PostConstruct
    private void init() {
        entity = new AccountEntity();
    }

    private Part picture;

    public String getRealName() {
        return entity.getReal_name();
    }

    public void setRealName(String realName) {
        entity.setReal_name(realName);
    }

    public String getNickname() {
        return entity.getNickname();
    }

    public void setNickname(String nickname) {
        entity.setNickname(nickname);
    }

    public String getPassword() {
        return entity.getPassword();
    }

    public void setPassword(String password) {
        entity.setPassword(password);
    }

    public String getBirthday() {
        return entity.getBirthday();
    }

    public void setBirthday(String birthday) {
        entity.setBirthday(birthday);
    }

    public Part getPicture() {
        return picture;
    }

    public void setPicture(Part picture) {
        this.picture = picture;
    }

    public void register() {
        //ar.createAccount(entity, picture);
    }
}
