package com.oodles.green.util;

import com.oodles.green.master.entity.OrganisationDatabaseDetail;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;

public final class DataSourceUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceUtil.class);

    public static DataSource createAndConfigureDataSource(OrganisationDatabaseDetail masterTenant) {
        HikariDataSource ds = new HikariDataSource();
        ds.setUsername(masterTenant.getDataBaseUserName());
        ds.setPassword(masterTenant.getDataBasePassword());
        ds.setJdbcUrl(masterTenant.getDataBaseUrl());
        ds.setDriverClassName(masterTenant.getDatabaseDriverClass());
        // HikariCP settings - could come from the master_tenant table but
        // hardcoded here for brevity
        // Maximum waiting time for a connection from the pool
        ds.setConnectionTimeout(20000);
        // Minimum number of idle connections in the pool
        ds.setMinimumIdle(3);
        // Maximum number of actual connection in the pool
        ds.setMaximumPoolSize(500);
        // Maximum time that a connection is allowed to sit idle in the pool
        ds.setIdleTimeout(300000);
        ds.setConnectionTimeout(20000);
        // Setting up a pool name for each tenant datasource
        String tenantConnectionPoolName = masterTenant.getDataBaseName() + "-connection-pool";
        ds.setPoolName(tenantConnectionPoolName);
        System.out.println("Configured datasource:" + masterTenant.getDataBaseName() + ". Connection pool name:" + tenantConnectionPoolName);
        return ds;
    }
}
