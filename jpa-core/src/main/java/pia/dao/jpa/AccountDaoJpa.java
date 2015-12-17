package pia.dao.jpa;

import pia.dao.AccountDao;
import pia.data.Account;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class AccountDaoJpa extends GenericDaoJpa<Account, Long> implements AccountDao {
    public AccountDaoJpa(EntityManager em) {
        super(em, Account.class);
    }

    @Override
    public Account findByNickname(String nickname) {
        Query q = em.createQuery("SELECT a FROM Account a WHERE a.nickname = :name", Account.class);
        q.setParameter("name", nickname);

        return (Account) q.getSingleResult();
    }
}
