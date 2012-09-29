/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.db.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author liaojian
 */
public class SimpleDataSource implements DataSource{
    private Stack<Connection> connectionsUsed = new Stack<Connection>();
//    private Stack<Connection> connectionPool = new Stack<Connection>();
    private List<ConnectionWrapper> connectionPool = new ArrayList<ConnectionWrapper>();
    
    private int poolSize = 10;
    private int loginTimeout = 30;
    private Driver driver;

    public SimpleDataSource(String url, String user, String password) {
        try {
            for(int i = 0; i < poolSize; i ++) {
                connectionPool.add(new ConnectionWrapper(DriverManager.getConnection(url, user, password)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SimpleDataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection() throws SQLException {
        ConnectionWrapper wrapper = getUnusedConnectionWrapper();
        if(wrapper == null) {
            throw new SQLException("can not malloc connection");
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
        for(ConnectionWrapper wrapper : connectionPool) {
            if(wrapper.isUsable()) {
                return wrapper;
            }
        }
        return null;
    }
}
