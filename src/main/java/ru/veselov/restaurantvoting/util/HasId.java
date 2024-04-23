package ru.veselov.restaurantvoting.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

public interface HasId {
    Integer getId();

    default void setId(Integer id) {
        throw new UnsupportedOperationException("Can't change id");
    }

    @JsonIgnore
    default boolean isNew() {
        return getId() == null;
    }


    // doesn't work for hibernate lazy proxy
    default int nonNullId() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
