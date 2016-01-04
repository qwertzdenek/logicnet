package pia.dao.jpa;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@JPADAO
public class AccountDaoJpa extends GenericDaoJpa<Account, String> implements AccountDao {
    public AccountDaoJpa() {
        super(Account.class);
    }

    @Override
    public Account findByNickname(String nickname) {
        TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a WHERE a.name = :name", Account.class);
        q.setParameter("name", nickname);

        return q.getSingleResult();
    }

    @Override
    public List<Account> listAll() {
        TypedQuery<Account> q = em.createQuery("SELECT a from Account a", Account.class);
        return q.getResultList();
    }
}
