package com.model.db.db.db.users_location.generated;

import com.model.db.db.db.users_location.UsersLocation;
import com.speedment.runtime.core.manager.Manager;
import javax.annotation.Generated;

/**
 * The generated base interface for the manager of every {@link
 * com.model.db.db.db.users_location.UsersLocation} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@Generated("Speedment")
public interface GeneratedUsersLocationManager extends Manager<UsersLocation> {
    
    @Override
    default Class<UsersLocation> getEntityClass() {
        return UsersLocation.class;
    }
}