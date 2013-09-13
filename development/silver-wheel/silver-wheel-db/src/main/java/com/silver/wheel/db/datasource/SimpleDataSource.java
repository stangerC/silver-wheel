package com.silver.wheel.db.datasource;

import com.silver.wheel.db.exception.DBRuntimeException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Liaojian
 */
public class SimpleDataSource implements DataSource {

    private List<ConnectionWrapper> usingConnections = new LinkedList<ConnectionWrapper>();
    private List<ConnectionWrapper> connectionPool = new LinkedList<ConnectionWrapper>();
    private int poolSize = 10;
    private int loginTimeout = 30;
    private String url;
    private String user;
    private String password;

    public SimpleDataSource(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        for (int i = 0; i < poolSize; i++) {
            connectionPool.add(mallocConnectionWrapper());
        }

    }

    private ConnectionWrapper mallocConnectionWrapper() {
        try {
            return new ConnectionWrapper(DriverManager.getConnection(url, user, password));
        } catch (SQLException ex) {
            throw new DBRuntimeException(ex);
        }
    }

    public Connection getConnection() {
        ConnectionWrapper wrapper = getUnusedConnectionWrapper();
        if (wrapper == null) {
            throw new DBRuntimeException("can not malloc connection");
        }        
        
        return wrapper.getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private ConnectionWrapper getUnusedConnectionWrapper() {
        ConnectionWrapper wrapper = null;
        
        if(connectionPool.size() > 0) {        
            for(Iterator<ConnectionWrapper> iterator = connectionPool.iterator(); iterator.hasNext(); ) {
                wrapper = iterator.next();
                iterator.remove();
                usingConnections.add(wrapper);
                break;
            }
        }
        else if( connectionPool.size() + usingConnections.size() < poolSize){
            wrapper = mallocConnectionWrapper();   
            connectionPool.add(wrapper);
        }
        else {
            cleanUpUsingConnection();
            if( connectionPool.size() + usingConnections.size() < poolSize){
                wrapper = mallocConnectionWrapper();  
                connectionPool.add(wrapper);
            }else {
                throw new DBRuntimeException("connection pool is full!");
            }
        }
        
        return wrapper;
    }

    private void cleanUpUsingConnection() {
        for(Iterator<ConnectionWrapper> iterator = usingConnections.iterator(); iterator.hasNext();) {
            ConnectionWrapper wrapper = iterator.next();
            if(wrapper.isClosed()) {
                iterator.remove();
            }
        }
    }
}
