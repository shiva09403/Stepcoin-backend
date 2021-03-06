package com.model.db.db.db.coin.generated;

import com.model.db.db.db.coin.Coin;
import com.model.db.db.db.coin.CoinImpl;
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
 * The generated Sql Adapter for a {@link com.model.db.db.db.coin.Coin} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@Generated("Speedment")
public abstract class GeneratedCoinSqlAdapter {
    
    private final TableIdentifier<Coin> tableIdentifier;
    
    protected GeneratedCoinSqlAdapter() {
        this.tableIdentifier = TableIdentifier.of("db", "db", "coin");
    }
    
    @ExecuteBefore(RESOLVED)
    void installMethodName(SqlStreamSupplierComponent streamSupplierComponent, SqlPersistenceComponent persistenceComponent) {
        streamSupplierComponent.install(tableIdentifier, this::apply);
        persistenceComponent.install(tableIdentifier);
    }
    
    protected Coin apply(ResultSet resultSet) throws SpeedmentException{
        final Coin entity = createEntity();
        try {
            entity.setId(resultSet.getInt(1));
            entity.setValue(getString(resultSet, 2));
            entity.setEnabled(getString(resultSet, 3));
            entity.setStartDate(getString(resultSet, 4));
            entity.setEndDate(getString(resultSet, 5));
            entity.setTaken(getString(resultSet, 6));
            entity.setTakenDate(getString(resultSet, 7));
            entity.setCreateTime(getTimestamp(resultSet, 8));
            entity.setStoreId(resultSet.getInt(9));
            entity.setLocationId(resultSet.getInt(10));
            entity.setUserId(getInt(resultSet, 11));
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return entity;
    }
    
    protected CoinImpl createEntity() {
        return new CoinImpl();
    }
}