package pia.cdi;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.Principal;
import java.util.Set;

@Named
@RequestScoped
public class UserController {
    @Inject
    @JPADAO
    AccountDao ad;

    @Inject
    Principal principal;

    public String getUsername() {
        if (principal != null) {
            return getAccount().getName();
        } else {
            return null;
        }
    }

    public Account getAccount() {
        return ad.findOne(principal.getName());
    }

    public Set<Account> getIncommingFriends() {
        return getAccount().getIncomingFriendRequests();
    }
}
