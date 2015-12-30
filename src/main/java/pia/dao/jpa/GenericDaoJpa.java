package pia.dao.jpa;

import pia.dao.GenericDao;
import pia.data.IEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

public abstract class GenericDaoJpa<E extends IEntity<PK>, PK extends Serializable> implements GenericDao<E, PK> {
    @PersistenceContext(unitName = "kiv.janecekz")
    protected EntityManager em;

    protected Class<E> persistedClass;

    /**
     * @param persistedClass entity type to be persisted by this instance
     */
    public GenericDaoJpa(Class<E> persistedClass) {
        this.persistedClass = persistedClass;
    }

    public E save(E instance) {
        if (instance.getPK() == null) {
            em.persist(instance);
            return instance;
        } else {
            return em.merge(instance);
        }
    }

    public void store(E instance) {
        em.persist(instance);
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
