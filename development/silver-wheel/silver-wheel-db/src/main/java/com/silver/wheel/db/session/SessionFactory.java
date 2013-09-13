package com.silver.wheel.db.session;

import com.silver.wheel.db.exception.DBRuntimeException;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Liaojian
 */
public class SessionFactory {
    private DataSource dataSource;
    
    public SessionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public JdbcSession createSession() {
        try {
            return new JdbcSession(dataSource.getConnection());
        } catch (SQLException ex) {
            throw new DBRuntimeException(ex);
        }
    }
}
