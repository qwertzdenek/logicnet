package pia.jetty;

import org.eclipse.jetty.jaas.spi.AbstractLoginModule;
import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.util.security.Password;
import pia.dao.AccountDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;
import pia.data.Role;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JPALoginService extends AbstractLoginModule {
    private static final String PERSISTENCE_UNIT = "kiv.janecekz.jetty";
    private AccountDao ad;
    private EntityManager em;

    private List<String> stringRoles(Set<Role> roles) {
        ArrayList<String> result = new ArrayList<>(roles.size());

        result.addAll(roles.stream().map(Role::getRole).collect(Collectors.toList()));

        return result;
    }

    @Override
    public UserInfo getUserInfo(String name) throws Exception {
        ad.getTransaction().begin();
        final Account account = ad.findByNickname(name);
        ad.getTransaction().commit();
        final Credential cred = new Password(account.getPassword());
        final List<String> roles = stringRoles(account.getRoles());
        UserInfo result = new UserInfo(account.getNickname(), cred, roles);

        return result;
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        super.initialize(subject, callbackHandler, sharedState, options);

        this.em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
        this.ad = new AccountDaoJpa(em);
    }

    // FIXME: em.close() on jetty destruction
}
