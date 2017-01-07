package com.model.db.db.db.coin.generated;

import com.model.db.db.db.coin.Coin;
import com.model.db.db.db.location.Location;
import com.model.db.db.db.store.Store;
import com.model.db.db.db.user.User;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.ComparableForeignKeyField;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.IntForeignKeyField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.OptionalInt;
import javax.annotation.Generated;

/**
 * The generated base for the {@link com.model.db.db.db.coin.Coin}-interface
 * representing entities of the {@code coin}-table in the database.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@Generated("Speedment")
public interface GeneratedCoin {
    
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getId()} method.
     */
    final IntField<Coin, Integer> ID = IntField.create(
        Identifier.ID,
        Coin::getId,
        Coin::setId,
        TypeMapper.primitive(), 
        true
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getValue()} method.
     */
    final StringField<Coin, String> VALUE = StringField.create(
        Identifier.VALUE,
        o -> OptionalUtil.unwrap(o.getValue()),
        Coin::setValue,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getEnabled()} method.
     */
    final StringField<Coin, String> ENABLED = StringField.create(
        Identifier.ENABLED,
        o -> OptionalUtil.unwrap(o.getEnabled()),
        Coin::setEnabled,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getStartDate()} method.
     */
    final StringField<Coin, String> START_DATE = StringField.create(
        Identifier.START_DATE,
        o -> OptionalUtil.unwrap(o.getStartDate()),
        Coin::setStartDate,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getEndDate()} method.
     */
    final StringField<Coin, String> END_DATE = StringField.create(
        Identifier.END_DATE,
        o -> OptionalUtil.unwrap(o.getEndDate()),
        Coin::setEndDate,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getTaken()} method.
     */
    final StringField<Coin, String> TAKEN = StringField.create(
        Identifier.TAKEN,
        o -> OptionalUtil.unwrap(o.getTaken()),
        Coin::setTaken,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getTakenDate()} method.
     */
    final StringField<Coin, String> TAKEN_DATE = StringField.create(
        Identifier.TAKEN_DATE,
        o -> OptionalUtil.unwrap(o.getTakenDate()),
        Coin::setTakenDate,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getCreateTime()} method.
     */
    final ComparableField<Coin, Timestamp, Timestamp> CREATE_TIME = ComparableField.create(
        Identifier.CREATE_TIME,
        o -> OptionalUtil.unwrap(o.getCreateTime()),
        Coin::setCreateTime,
        TypeMapper.identity(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getStoreId()} method.
     */
    final IntForeignKeyField<Coin, Integer, Store> STORE_ID = IntForeignKeyField.create(
        Identifier.STORE_ID,
        Coin::getStoreId,
        Coin::setStoreId,
        Store.ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getLocationId()} method.
     */
    final IntForeignKeyField<Coin, Integer, Location> LOCATION_ID = IntForeignKeyField.create(
        Identifier.LOCATION_ID,
        Coin::getLocationId,
        Coin::setLocationId,
        Location.ID,
        TypeMapper.primitive(), 
        false
    );
    /**
     * This Field corresponds to the {@link Coin} field that can be obtained
     * using the {@link Coin#getUserId()} method.
     */
    final ComparableForeignKeyField<Coin, Integer, Integer, User> USER_ID = ComparableForeignKeyField.create(
        Identifier.USER_ID,
        o -> OptionalUtil.unwrap(o.getUserId()),
        Coin::setUserId,
        User.ID,
        TypeMapper.identity(), 
        false
    );
    
    /**
     * Returns the id of this Coin. The id field corresponds to the database
     * column db.db.coin.id.
     * 
     * @return the id of this Coin
     */
    int getId();
    
    /**
     * Returns the value of this Coin. The value field corresponds to the
     * database column db.db.coin.value.
     * 
     * @return the value of this Coin
     */
    Optional<String> getValue();
    
    /**
     * Returns the enabled of this Coin. The enabled field corresponds to the
     * database column db.db.coin.enabled.
     * 
     * @return the enabled of this Coin
     */
    Optional<String> getEnabled();
    
    /**
     * Returns the startDate of this Coin. The startDate field corresponds to
     * the database column db.db.coin.startDate.
     * 
     * @return the startDate of this Coin
     */
    Optional<String> getStartDate();
    
    /**
     * Returns the endDate of this Coin. The endDate field corresponds to the
     * database column db.db.coin.endDate.
     * 
     * @return the endDate of this Coin
     */
    Optional<String> getEndDate();
    
    /**
     * Returns the taken of this Coin. The taken field corresponds to the
     * database column db.db.coin.taken.
     * 
     * @return the taken of this Coin
     */
    Optional<String> getTaken();
    
    /**
     * Returns the takenDate of this Coin. The takenDate field corresponds to
     * the database column db.db.coin.takenDate.
     * 
     * @return the takenDate of this Coin
     */
    Optional<String> getTakenDate();
    
    /**
     * Returns the createTime of this Coin. The createTime field corresponds to
     * the database column db.db.coin.createTime.
     * 
     * @return the createTime of this Coin
     */
    Optional<Timestamp> getCreateTime();
    
    /**
     * Returns the storeId of this Coin. The storeId field corresponds to the
     * database column db.db.coin.store_id.
     * 
     * @return the storeId of this Coin
     */
    int getStoreId();
    
    /**
     * Returns the locationId of this Coin. The locationId field corresponds to
     * the database column db.db.coin.location_id.
     * 
     * @return the locationId of this Coin
     */
    int getLocationId();
    
    /**
     * Returns the userId of this Coin. The userId field corresponds to the
     * database column db.db.coin.user_id.
     * 
     * @return the userId of this Coin
     */
    OptionalInt getUserId();
    
    /**
     * Sets the id of this Coin. The id field corresponds to the database column
     * db.db.coin.id.
     * 
     * @param id to set of this Coin
     * @return   this Coin instance
     */
    Coin setId(int id);
    
    /**
     * Sets the value of this Coin. The value field corresponds to the database
     * column db.db.coin.value.
     * 
     * @param value to set of this Coin
     * @return      this Coin instance
     */
    Coin setValue(String value);
    
    /**
     * Sets the enabled of this Coin. The enabled field corresponds to the
     * database column db.db.coin.enabled.
     * 
     * @param enabled to set of this Coin
     * @return        this Coin instance
     */
    Coin setEnabled(String enabled);
    
    /**
     * Sets the startDate of this Coin. The startDate field corresponds to the
     * database column db.db.coin.startDate.
     * 
     * @param startDate to set of this Coin
     * @return          this Coin instance
     */
    Coin setStartDate(String startDate);
    
    /**
     * Sets the endDate of this Coin. The endDate field corresponds to the
     * database column db.db.coin.endDate.
     * 
     * @param endDate to set of this Coin
     * @return        this Coin instance
     */
    Coin setEndDate(String endDate);
    
    /**
     * Sets the taken of this Coin. The taken field corresponds to the database
     * column db.db.coin.taken.
     * 
     * @param taken to set of this Coin
     * @return      this Coin instance
     */
    Coin setTaken(String taken);
    
    /**
     * Sets the takenDate of this Coin. The takenDate field corresponds to the
     * database column db.db.coin.takenDate.
     * 
     * @param takenDate to set of this Coin
     * @return          this Coin instance
     */
    Coin setTakenDate(String takenDate);
    
    /**
     * Sets the createTime of this Coin. The createTime field corresponds to the
     * database column db.db.coin.createTime.
     * 
     * @param createTime to set of this Coin
     * @return           this Coin instance
     */
    Coin setCreateTime(Timestamp createTime);
    
    /**
     * Sets the storeId of this Coin. The storeId field corresponds to the
     * database column db.db.coin.store_id.
     * 
     * @param storeId to set of this Coin
     * @return        this Coin instance
     */
    Coin setStoreId(int storeId);
    
    /**
     * Sets the locationId of this Coin. The locationId field corresponds to the
     * database column db.db.coin.location_id.
     * 
     * @param locationId to set of this Coin
     * @return           this Coin instance
     */
    Coin setLocationId(int locationId);
    
    /**
     * Sets the userId of this Coin. The userId field corresponds to the
     * database column db.db.coin.user_id.
     * 
     * @param userId to set of this Coin
     * @return       this Coin instance
     */
    Coin setUserId(Integer userId);
    
    /**
     * Queries the specified manager for the referenced Store. If no such Store
     * exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    Store findStoreId(Manager<Store> foreignManager);
    
    /**
     * Queries the specified manager for the referenced Location. If no such
     * Location exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    Location findLocationId(Manager<Location> foreignManager);
    
    /**
     * Queries the specified manager for the referenced User. If no such User
     * exists, an {@code NullPointerException} will be thrown.
     * 
     * @param foreignManager the manager to query for the entity
     * @return               the foreign entity referenced
     */
    Optional<User> findUserId(Manager<User> foreignManager);
    
    enum Identifier implements ColumnIdentifier<Coin> {
        
        ID ("id"),
        VALUE ("value"),
        ENABLED ("enabled"),
        START_DATE ("startDate"),
        END_DATE ("endDate"),
        TAKEN ("taken"),
        TAKEN_DATE ("takenDate"),
        CREATE_TIME ("createTime"),
        STORE_ID ("store_id"),
        LOCATION_ID ("location_id"),
        USER_ID ("user_id");
        
        private final String columnName;
        private final TableIdentifier<Coin> tableIdentifier;
        
        Identifier(String columnName) {
            this.columnName = columnName;
            this.tableIdentifier = TableIdentifier.of(getDbmsName(), getSchemaName(), getTableName());
        }
        
        @Override
        public String getDbmsName() {
            return "db";
        }
        
        @Override
        public String getSchemaName() {
            return "db";
        }
        
        @Override
        public String getTableName() {
            return "coin";
        }
        
        @Override
        public String getColumnName() {
            return this.columnName;
        }
        
        @Override
        public TableIdentifier<Coin> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }
}