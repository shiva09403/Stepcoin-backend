package com.model.db.db.db.user.generated;

import com.model.db.db.db.user.User;
import com.model.db.db.db.user.UserImpl;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.component.sql.SqlStreamSupplierComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Generated;
import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.runtime.core.internal.util.sql.ResultSetUtil.*;

/**
 * The generated Sql Adapter for a {@link com.model.db.db.db.user.User} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@Generated("Speedment")
public abstract class GeneratedUserSqlAdapter {
    
    private final TableIdentifier<User> tableIdentifier;
    
    protected GeneratedUserSqlAdapter() {
        this.tableIdentifier = TableIdentifier.of("db", "db", "user");
    }
    
    @ExecuteBefore(RESOLVED)
    void installMethodName(SqlStreamSupplierComponent streamSupplierComponent, SqlPersistenceComponent persistenceComponent) {
        streamSupplierComponent.install(tableIdentifier, this::apply);
        persistenceComponent.install(tableIdentifier);
    }
    
    protected User apply(ResultSet resultSet) throws SpeedmentException{
        final User entity = createEntity();
        try {
            entity.setId(resultSet.getInt(1));
            entity.setEmail(getString(resultSet, 2));
            entity.setPassword(getString(resultSet, 3));
            entity.setPhoneNumber(getString(resultSet, 4));
            entity.setCredits(getString(resultSet, 5));
            entity.setCreateTime(getTimestamp(resultSet, 6));
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return entity;
    }
    
    protected UserImpl createEntity() {
        return new UserImpl();
    }
}