package pia.beans;

import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.dao.RoleDao;
import pia.data.Account;
import pia.data.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Stateless
public class MaintenanceBean {
    @Inject
    @JPADAO
    private AccountDao ad;

    @Inject
    @JPADAO
    private RoleDao rd;

    public void seed() {
        Role user = new Role();
        user.setRole("user");
        Account root = new Account();
        root.setBirthday(new Date(0));
        root.setNickname("root");
        root.setPassword("strong");
        root.setProfilePicture("neco.png");
        Set<Role> list = new HashSet<>(1);
        list.add(user);
        root.setRoles(list);

        ad.getTransaction().begin();
        rd.save(user);
        ad.save(root);
        ad.getTransaction().commit();
    }
}
