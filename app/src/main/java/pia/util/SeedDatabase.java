package pia.util;

import pia.data.Account;
import pia.data.Role;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class SeedDatabase {
    private static void persistSeed() {
        EntityManager em = DBManager.createEntityManager();
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

        em.getTransaction().begin();
        em.persist(user);
        em.persist(root);
        em.getTransaction().commit();
    }
}
