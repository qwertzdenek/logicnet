package pia.data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Base interface for all entities to make implementation of generic dao easier.
 * <p>
 * PK type represents type of the entity's primary key.
 *
 * @author Zdenek Janecek
 */
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> implements Serializable, IEntity<PK> {
    protected PK id;

    @Transient
    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    @Override
    @Transient
    public PK getPK() {
        return getId();
    }

    public abstract String toString();

    public abstract boolean equals(Object o);

    public abstract int hashCode();
}
