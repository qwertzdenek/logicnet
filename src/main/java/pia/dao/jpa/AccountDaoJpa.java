package pia.dao.jpa;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.data.Account;
import pia.data.Post;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@JPADAO
public class AccountDaoJpa extends GenericDaoJpa<Account, String> implements AccountDao {
    public AccountDaoJpa() {
        super(Account.class);
    }

    @Override
    public int postCount(Account account) {
        Query q = em.createQuery("SELECT size(a.posts) FROM Account a WHERE a = :account");
        q.setParameter("account", account);
        return (int) q.getSingleResult();
    }

    @Override
    public int likesCount(Account account) {
        Query q = em.createQuery("SELECT size(a.likedPosts) FROM Account a WHERE a = :account");
        q.setParameter("account", account);
        return (int) q.getSingleResult();
    }

    @Override
    public List<Account> fetchIncomingFriends(Account account) {
        TypedQuery<Account> q = em.createQuery("SELECT f FROM Account a JOIN a.incomingFriendRequests f WHERE a = :account", Account.class);
        q.setParameter("account",account);
        return q.getResultList();
    }

    @Override
    public List<Account> listAll() {
        TypedQuery<Account> q = em.createQuery("SELECT a FROM Account a", Account.class);
        return q.getResultList();
    }

    @Override
    public long accountCount() {
        Query q = em.createQuery("SELECT count(a.id) FROM Account a");
        return (long) q.getSingleResult();
    }
}
