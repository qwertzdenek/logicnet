package pia.dao;

import pia.data.Account;
import pia.data.Post;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

public interface AccountDao extends GenericDao<Account, String> {
    int postCount(Account account);
    int likesCount(Account account);

    List<Account> fetchIncomingFriends(Account account);
    List<Account> listAll();
    long accountCount();
}
