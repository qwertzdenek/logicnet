package pia.jaas;


import pia.dao.AccountDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.data.Account;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class JPALoginService implements LoginModule {
    private static final String PERSISTENCE_UNIT = "kiv.janecekz.jaas";
    private AccountDao ad;
    private EntityManager em;

    private CallbackHandler handler;
    private Subject subject;
    private AccountPrincipal accountPrincipal;
    private RolePrincipal rolePrincipal;

    private MessageDigest md;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        // TODO: do it as singleton bean
        this.em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();
        this.ad = new AccountDaoJpa(em);

        // Store the handler
        this.handler = callbackHandler;

        // Subject reference holds the principals
        this.subject = subject;

        try {
            this.md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("SHA-256 NOT AVAILABLE!");
        }
    }

    private boolean checkUserPassword(String accountName, String testedPassword) {
        // read password from the database
        ad.getTransaction().begin();
        final Account account = ad.findByNickname(accountName);
        ad.getTransaction().commit();

        String realPassword = account.getPassword();

        md.update(realPassword.getBytes());
        byte[] realPasswordObf = md.digest();

        md.update(testedPassword.getBytes());
        byte[] testedPasswordObf = md.digest();

        return MessageDigest.isEqual(realPasswordObf, testedPasswordObf);
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("login");
        callbacks[1] = new PasswordCallback("password", true);

        try {
            handler.handle(callbacks);
            String name = ((NameCallback) callbacks[0]).getName();
            String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

            if (name != null && !password.isEmpty() && checkUserPassword(name, password)) {
                accountPrincipal = new AccountPrincipal(name);
                rolePrincipal = new RolePrincipal("user"); // we have only user role
                return true;
            }

            // If credentials are NOT OK we throw a LoginException
            throw new LoginException("Authentication failed");
        } catch (IOException e) {
            throw new LoginException(e.getMessage());
        } catch (UnsupportedCallbackException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean commit() throws LoginException {
        subject.getPrincipals().add(accountPrincipal);
        subject.getPrincipals().add(rolePrincipal);

        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        accountPrincipal = null;
        rolePrincipal = null;
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(accountPrincipal);
        subject.getPrincipals().remove(rolePrincipal);
        return true;
    }
}
