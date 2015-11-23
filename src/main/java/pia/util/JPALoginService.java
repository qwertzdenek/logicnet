package pia.util;

import org.eclipse.jetty.jaas.spi.AbstractLoginModule;
import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;
import pia.dao.AccountDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;
import pia.data.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JPALoginService extends AbstractLoginModule {
    private AccountDao ad;

    private List<String> stringRoles(Set<Role> roles) {
        ArrayList<String> result = new ArrayList<>(roles.size());

        int i = 0;
        for (Role role : roles) {
            result.set(i, role.getRole());
            i++;
        }

        return result;
    }

    @Override
    public UserInfo getUserInfo(String s) throws Exception {
        if (ad == null) {
            this.ad = new AccountDaoJpa();
        }

        final Account account = ad.findByNickname(s);
        final Credential cred = Credential.getCredential(account.getPassword());
        final List<String> roles = stringRoles(account.getRoles());
        return new UserInfo(account.getNickname(), cred, roles);
    }
}
