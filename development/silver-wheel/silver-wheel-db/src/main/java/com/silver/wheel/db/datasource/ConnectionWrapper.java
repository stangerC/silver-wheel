/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.db.datasource;

import com.silver.wheel.db.exception.DBRuntimeException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CN085910
 */
public class ConnectionWrapper {
    private Connection connection;
    private String name;
    
    public ConnectionWrapper(Connection connection) {
        this.connection = connection;       
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isClosed() {
        try {
            if(connection.isClosed()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new DBRuntimeException(ex);
        }
    }      
    
}
