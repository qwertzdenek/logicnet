package pia.dao.jpa;

import pia.dao.AccountDao;
import pia.data.Account;

public class AccountDaoJpa extends GenericDaoJpa<Account, Long> implements AccountDao {
    public AccountDaoJpa() {
        super(Account.class);
    }
}
