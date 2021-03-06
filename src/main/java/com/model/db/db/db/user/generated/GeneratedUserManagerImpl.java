package com.model.db.db.db.user.generated;

import com.model.db.db.db.user.User;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.AbstractManager;
import com.speedment.runtime.field.Field;
import java.util.stream.Stream;
import javax.annotation.Generated;

/**
 * The generated base implementation for the manager of every {@link
 * com.model.db.db.db.user.User} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@Generated("Speedment")
public abstract class GeneratedUserManagerImpl extends AbstractManager<User> implements GeneratedUserManager {
    
    private final TableIdentifier<User> tableIdentifier;
    
    protected GeneratedUserManagerImpl() {
        this.tableIdentifier = TableIdentifier.of("db", "db", "user");
    }
    
    @Override
    public TableIdentifier<User> getTableIdentifier() {
        return tableIdentifier;
    }
    
    @Override
    public Stream<Field<User>> fields() {
        return Stream.of(
            User.ID,
            User.EMAIL,
            User.PASSWORD,
            User.PHONE_NUMBER,
            User.CREDITS,
            User.CREATE_TIME,
            User.NOTIFICATION_ID,
            User.NOTIFICATION_TIME
        );
    }
    
    @Override
    public Stream<Field<User>> primaryKeyFields() {
        return Stream.of(
            User.ID
        );
    }
}