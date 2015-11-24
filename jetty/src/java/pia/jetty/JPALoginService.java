package pia.jetty;

import org.eclipse.jetty.jaas.spi.AbstractLoginModule;
import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.util.security.Password;
import pia.dao.AccountDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;
import pia.data.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JPALoginService extends AbstractLoginModule {
    private AccountDao ad;

    private List<String> stringRoles(Set<Role> roles) {
        ArrayList<String> result = new ArrayList<>(roles.size());

        result.addAll(roles.stream().map(Role::getRole).collect(Collectors.toList()));

        return result;
    }

    @Override
    public UserInfo getUserInfo(String name) throws Exception {
        if (ad == null) {
            this.ad = new AccountDaoJpa();
        }

        final Account account = ad.findByNickname(name);
        final Credential cred = new Password(account.getPassword());
        final List<String> roles = stringRoles(account.getRoles());
        UserInfo result = new UserInfo(account.getNickname(), cred, roles);

        return result;
    }
}
