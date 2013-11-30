package com.silver.wheel.db.jdbc;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Liaojian
 */
public interface ProcessingAction {
    public List doProcess(ResultSet rs);
}
