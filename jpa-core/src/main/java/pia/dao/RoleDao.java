package pia.dao;

import pia.data.Account;
import pia.data.Role;

import javax.persistence.NoResultException;

/**
 * DAO interface for the User entity
 * <p>
 * Date: 26.9.15
 *
 * @author Jakub Danek
 */
public interface RoleDao extends GenericDao<Role, Long> {
}
