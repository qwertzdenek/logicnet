package pia.util;

import pia.data.Account;
import pia.data.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@WebListener
public class DBManager implements ServletContextListener {
    private static final String PERSISTENCE_UNIT = "kiv.janecekz";

    private static EntityManagerFactory emf;

    private void persistSeed() {
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

        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.persist(root);
        em.getTransaction().commit();
    }

    public void contextInitialized(ServletContextEvent event) {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        persistSeed();
    }

    public void contextDestroyed(ServletContextEvent event) {
        emf.close();
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return emf.createEntityManager();
    }
}
