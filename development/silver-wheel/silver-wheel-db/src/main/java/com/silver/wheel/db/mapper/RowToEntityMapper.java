package com.silver.wheel.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Liaojian
 */
public interface RowToEntityMapper<T> {
    public T mapping(ResultSet rs) throws SQLException;
}
