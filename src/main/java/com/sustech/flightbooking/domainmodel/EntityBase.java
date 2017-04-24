package com.sustech.flightbooking.domainmodel;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Henry on 4/19/2017.
 */

public abstract class EntityBase {

    protected UUID id;

    public EntityBase(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityBase)) {
            return false;
        }
        EntityBase entity = (EntityBase) o;
        return Objects.equals(this.id, entity.id);
    }
}
