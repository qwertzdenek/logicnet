package pia.dao;

import pia.data.Account;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Set;

/**
 * DAO interface for the User entity
 * <p>
 * Date: 26.9.15
 *
 * @author Jakub Danek
 */
public interface AccountDao extends GenericDao<Account, String> {
    Account findByNickname(String username) throws NoResultException;
    List<Account> listAll() throws NoResultException;
}
