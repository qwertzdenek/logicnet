package pia.dao;

import pia.data.Account;

/**
 * DAO interface for the User entity
 * <p>
 * Date: 26.9.15
 *
 * @author Jakub Danek
 */
public interface AccountDao extends GenericDao<Account, Long> {
    Account findByNickname(String username);
}
