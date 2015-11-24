package pia.dao.jpa;

import pia.dao.GenericDao;
import pia.data.IEntity;
import pia.util.DBManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

public class GenericDaoJpa<E extends IEntity<PK>, PK extends Serializable> implements GenericDao<E, PK> {
    protected EntityManager em;
    protected Class<E> persistedClass;

    /**
     * @param persistedClass entity type to be persisted by this instance
     */
    public GenericDaoJpa(Class<E> persistedClass) {
        this.em = DBManager.createEntityManager();
        this.persistedClass = persistedClass;
    }

    public EntityTransaction getTransaction() {
        return this.em.getTransaction();
    }

    public E save(E instance) {
        if (instance.getPK() == null) {
            em.persist(instance);
            return instance;
        } else {
            return em.merge(instance);
        }
    }

    public E findOne(PK id) {
        return em.find(persistedClass, id);
    }

    public void delete(PK id) {
        E en = em.find(persistedClass, id);
        if (en != null) {
            em.remove(en);
        }
    }
}
