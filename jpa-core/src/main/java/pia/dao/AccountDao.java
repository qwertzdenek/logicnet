package pia.dao;

import pia.data.Account;

import javax.persistence.NoResultException;

/**
 * DAO interface for the User entity
 * <p>
 * Date: 26.9.15
 *
 * @author Jakub Danek
 */
public interface AccountDao extends GenericDao<Account, Long> {
    Account findByNickname(String username) throws NoResultException;
}
