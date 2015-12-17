package pia.dao.jpa;

import pia.dao.RoleDao;
import pia.data.Role;

import javax.persistence.EntityManager;

public class RoleDaoJpa extends GenericDaoJpa<Role, Long> implements RoleDao {
    public RoleDaoJpa(EntityManager em) {
        super(em, Role.class);
    }
}
