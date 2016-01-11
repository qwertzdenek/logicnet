package pia.dao;

import pia.data.Account;
import pia.data.Friendship;

import java.util.List;

public interface FriendshipDao extends GenericDao<Friendship, Integer> {
    void addFriendship(Account one, Account two);
    List<Account> allFriends(Account account);
    long friendCount(Account account);
}
