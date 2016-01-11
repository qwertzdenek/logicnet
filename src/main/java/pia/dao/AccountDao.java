package pia.dao;

import pia.data.Account;

import java.util.List;

public interface AccountDao extends GenericDao<Account, String> {
    int postCount(Account account);
    int likesCount(Account account);

    List<Account> fetchIncomingFriends(Account account);
    List<Account> listAll();
    long accountCount();
}
