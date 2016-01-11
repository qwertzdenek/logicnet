package pia.dao.jpa;

import pia.dao.AccountDao;
import pia.dao.FriendshipDao;
import pia.dao.JPADAO;
import pia.data.Account;
import pia.data.Friendship;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@JPADAO
public class FriendshipDaoJpa extends GenericDaoJpa<Friendship, Integer> implements FriendshipDao {
    @Inject
    @JPADAO
    AccountDao ad;

    public FriendshipDaoJpa() {
        super(Friendship.class);
    }

    @Override
    public void addFriendship(Account target, Account source) {
        target.getFriendRequests().remove(source);

        Friendship f = new Friendship();
        f.setDate(Timestamp.from(Instant.now()));
        f.setFirst(source);
        f.setSecond(target);

        save(f);
    }

    @Override
    public List<Account> allFriends(Account account) {
        TypedQuery<Account> first = em.createQuery("SELECT f.first FROM Friendship f WHERE f.second = :account", Account.class);
        first.setParameter("account", account);

        TypedQuery<Account> second = em.createQuery("SELECT f.second FROM Friendship f WHERE f.first = :account", Account.class);
        second.setParameter("account", account);

        List<Account> result = first.getResultList();
        result.addAll(second.getResultList());

        return result;
    }

    @Override
    public long friendCount(Account account) {
        Query first = em.createQuery("SELECT count(f.first.id) FROM Friendship f WHERE f.second = :account");
        first.setParameter("account", account);

        Query second = em.createQuery("SELECT count(f.second.id) FROM Friendship f WHERE f.first = :account");
        second.setParameter("account", account);

        return (long) first.getSingleResult() + (long) second.getSingleResult();
    }
}
