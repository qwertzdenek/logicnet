package pia.dao;

import pia.data.IEntity;

import java.io.Serializable;

/**
 * Base interface for DAOs, providing CRUD operations.
 * <p>
 * Date: 26.9.15
 *
 * @author Jakub Danek
 */
public interface GenericDao<E extends IEntity<PK>, PK extends Serializable> {
    /**
     * Either inserts new or updates existing instance.
     *
     * @param instance to be persisted
     * @return persisted instance
     */
    E save(E instance);

    /**
     * It will persist entity even if has PK. You have to set PK before!
     * @param instance
     */
    void store(E instance);

    /**
     * @param id
     * @return instance with the given id or null if not found
     */
    E findOne(PK id);

    /**
     * Removes the given entity from persistence.
     *
     * @param id if of the entity instance
     */
    void delete(PK id);
}
