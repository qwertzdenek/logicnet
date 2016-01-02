package pia.beans;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;

import javax.ejb.Stateful;
import javax.inject.Inject;
import java.io.Serializable;

@Stateful
public class AccountService implements Serializable {
    @JPADAO
    @Inject
    AccountDao ad;

    public void createRegistration(Account account) {
        ad.store(account);
    }

    public Account getAccount(String id) {
        return ad.findOne(id);
    }
}
