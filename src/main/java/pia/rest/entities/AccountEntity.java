package pia.rest.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean used for JAX-RS conversions and Facelets storage with validation.
 */
@XmlRootElement
public class AccountEntity {
    private String real_name;
    private String nickname;
    private String password;
    private String birthday;

    @NotNull
    @Size(min = 8)
    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    @NotNull
    @Size(min = 4)
    @Pattern(regexp = "\\w+", message = "This is not valid nickname.'")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @NotNull
    @Size(min = 4)
    // TODO: pass complexity
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Pattern(regexp = "\\d{1,2}/\\d{1,2}/\\d{4}")
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
