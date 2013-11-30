package com.silver.wheel.db.session;

import com.silver.wheel.db.exception.DBRuntimeException;
import com.silver.wheel.db.jdbc.ResultSetHandler;
import com.silver.wheel.db.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Liaojian
 */
public class JdbcSession {
    private boolean isClosed;    
    private Connection conn;
    
    public JdbcSession(Connection conn) {
        try {
            this.conn = conn;
            if(conn.isClosed()) {
                throw new DBRuntimeException("connection is closed!");
            }
            isClosed = false;
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DBRuntimeException(ex);
        }
    }
        
    public <T> List<T> query(String sql, ResultSetHandler<List<T>> handler) {                
        PreparedStatement pst = null;
        ResultSet rs = null;    
        
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            List<T>result = handler.handle(rs);                        
            
            return result;
        } catch (SQLException ex) {            
            throw new DBRuntimeException(ex);
        }finally {
            JdbcUtils.close(rs, pst, null);
        }                
    }
    
    public int update(String sql) {
        return update(sql, null, null);
    }
    /**
     * 执行更新（包括删除、插入）语句
     * @param sql
     * @param params
     * @param handler
     * @return 
     */
    public int update(String sql, Object[] params, ParameterHandler handler) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            
            if(params != null && params.length > 0) {
                handler.setParameter(pst, params);
            }
            
            int result = pst.executeUpdate();                                                       
            return result;
        } catch (SQLException ex) {            
            throw new DBRuntimeException(ex);
        }finally {
            JdbcUtils.closePreparedStatement(pst);
        }   
    }
    
    public boolean execute(String sql) {   
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            boolean result = pst.execute(sql);                                             
            
            return result;
        } catch (SQLException ex) {            
            throw new DBRuntimeException(ex);
        }finally {
            JdbcUtils.closePreparedStatement(pst);
        }   
    }
    
    /**
     * 关闭Session。方法会提交未提交的事务，关闭数据库连接。
     */    
    public void close() {
        //检查session是否已经关闭
        if(this.isClosed) {
            throw new DBRuntimeException("session has been closed!");
        }
        isClosed = true;
        try {
            conn.commit();            
        } catch (SQLException ex) {
            throw new DBRuntimeException(ex);
        } finally {
            JdbcUtils.closeConnection(conn);
        }
    }
    
    /**
     * 提交事务
     */
    public void commit() {
        try {
            conn.commit();
        } catch (SQLException ex) {
            throw new DBRuntimeException(ex);
        }
    }
    /**
     * 回滚事务
     */
    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new DBRuntimeException(ex);
        }
    }
}
