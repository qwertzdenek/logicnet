package pia.dao.jpa;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;

import javax.persistence.Query;

@JPADAO
public class AccountDaoJpa extends GenericDaoJpa<Account, String> implements AccountDao {
    public AccountDaoJpa() {
        super(Account.class);
    }

    @Override
    public Account findByNickname(String nickname) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.name = :name", Account.class);
        q.setParameter("name", nickname);

        return (Account) q.getSingleResult();
    }
}
