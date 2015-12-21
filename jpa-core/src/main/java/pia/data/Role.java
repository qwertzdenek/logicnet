package pia.data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role extends BaseEntity<Long> {
    String role;
    Set<Account> users;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ManyToMany(mappedBy="roles")
    public Set<Account> getUsers() {
        return users;
    }

    public void setUsers(Set<Account> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return this.role.equals(role.role);

    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }

    @Override
    public String toString() {
        return role;
    }
}
