package com.silver.wheel.db.jdbc;

import java.sql.ResultSet;

/**
 *
 * @author Liaojian
 */
public interface ResultSetHandler<T> {
    public T handle(ResultSet rs);
}
