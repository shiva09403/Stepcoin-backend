package com.model.db.generated;

import com.model.db.DbApplication;
import com.model.db.DbApplicationBuilder;
import com.model.db.DbApplicationImpl;
import com.model.db.db.db.business_user.BusinessUserManagerImpl;
import com.model.db.db.db.business_user.BusinessUserSqlAdapter;
import com.model.db.db.db.coin.CoinManagerImpl;
import com.model.db.db.db.coin.CoinSqlAdapter;
import com.model.db.db.db.location.LocationManagerImpl;
import com.model.db.db.db.location.LocationSqlAdapter;
import com.model.db.db.db.store.StoreManagerImpl;
import com.model.db.db.db.store.StoreSqlAdapter;
import com.model.db.db.db.user.UserManagerImpl;
import com.model.db.db.db.user.UserSqlAdapter;
import com.model.db.db.db.users_location.UsersLocationManagerImpl;
import com.model.db.db.db.users_location.UsersLocationSqlAdapter;
import com.speedment.common.injector.Injector;
import com.speedment.runtime.core.internal.AbstractApplicationBuilder;
import javax.annotation.Generated;

/**
 * A generated base {@link
 * com.speedment.runtime.core.internal.AbstractApplicationBuilder} class for the
 * {@link com.speedment.runtime.config.Project} named db.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@Generated("Speedment")
public abstract class GeneratedDbApplicationBuilder extends AbstractApplicationBuilder<DbApplication, DbApplicationBuilder> {
    
    protected GeneratedDbApplicationBuilder() {
        super(DbApplicationImpl.class, GeneratedDbMetadata.class);
        withManager(BusinessUserManagerImpl.class);
        withManager(CoinManagerImpl.class);
        withManager(LocationManagerImpl.class);
        withManager(StoreManagerImpl.class);
        withManager(UserManagerImpl.class);
        withManager(UsersLocationManagerImpl.class);
        withComponent(BusinessUserSqlAdapter.class);
        withComponent(CoinSqlAdapter.class);
        withComponent(LocationSqlAdapter.class);
        withComponent(StoreSqlAdapter.class);
        withComponent(UserSqlAdapter.class);
        withComponent(UsersLocationSqlAdapter.class);
    }
    
    @Override
    public DbApplication build(Injector injector) {
        return injector.getOrThrow(DbApplication.class);
    }
}