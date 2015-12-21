package pia.data;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity<Long> {
    private String nickname;
    private String password;
    private Date birthday;
    private String profilePicture;

    private Set<Role> roles;

    public Account() {
        this.roles = new LinkedHashSet<>();
    }

    @Column(nullable = false)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(value = TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(nullable = false)
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_roles",
            joinColumns = @JoinColumn(name = "account", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Account{" +
                "nickname='" + nickname + '\'' +
                ", birthday=" + birthday +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!nickname.equals(account.nickname)) return false;
        if (!password.equals(account.password)) return false;
        if (birthday != null ? !birthday.equals(account.birthday) : account.birthday != null) return false;
        return profilePicture.equals(account.profilePicture);

    }

    @Override
    public int hashCode() {
        int result = nickname.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + profilePicture.hashCode();
        return result;
    }
}
