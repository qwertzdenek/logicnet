package pia.util;

import pia.dao.AccountDao;
import pia.dao.RoleDao;
import pia.dao.jpa.AccountDaoJpa;
import pia.dao.jpa.RoleDaoJpa;
import pia.data.Account;
import pia.data.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DBManager implements ServletContextListener {
    private static final String PERSISTENCE_UNIT = "kiv.janecekz";

    private static EntityManagerFactory emf;

    private void persistSeed() {
        EntityManager em = createEntityManager();
        AccountDao ad = new AccountDaoJpa(em);
        RoleDao rd = new RoleDaoJpa(em);

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

        em.close();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        persistSeed();

        EntityManager em = createEntityManager();
        AccountDao ad = new AccountDaoJpa(em);
        System.out.println("Created "+ad.findByNickname("root").toString());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        emf.close();
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return emf.createEntityManager();
    }
}
