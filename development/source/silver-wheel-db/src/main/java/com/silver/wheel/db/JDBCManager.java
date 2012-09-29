/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * 
 * @author liaojian
 */
public class JDBCManager {
    public DataSource getDataSource(String dsName) {
        return null;
    }
    
    public static void CloseAll(Connection conn, PreparedStatement pst, ResultSet rs) {
        if(rs != null) {
            try {
                if(!rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException ex) {
                rs = null;
                Logger.getLogger(JDBCManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        if(pst != null) {
            try {
                if(!pst.isClosed()) {
                    pst.close();
                }
            } catch (SQLException ex) {
                pst = null;
                Logger.getLogger(JDBCManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        if(conn != null) {
            try {
                if(!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                conn = null;
                Logger.getLogger(JDBCManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }     
}
