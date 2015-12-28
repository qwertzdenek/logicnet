package pia.dao.jpa;

import pia.dao.JPADAO;
import pia.dao.RoleDao;
import pia.data.Role;

@JPADAO
public class RoleDaoJpa extends GenericDaoJpa<Role, Long> implements RoleDao {
    public RoleDaoJpa() {
        super(Role.class);
    }
}
