package com.silver.wheel.db.datasource;

import javax.sql.DataSource;

/**
 *
 * @author Liaojian
 */
public interface DataSourceFactory {
    public DataSource getDataSource(String dsName);
}
