package pia.jaas;

import java.security.Principal;

/**
 * Holds a single role name that the user is in
 *
 * @author sixthpoint
 */
public class AccountPrincipal implements Principal {

    private String name;

    public AccountPrincipal(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
