/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.db.utils;

import com.silver.wheel.db.exception.DBRuntimeException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Liaojian
 */
public class JdbcUtils {

    public static void closeResultSet(ResultSet rs) throws DBRuntimeException {
        if (rs != null) {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException ex) {
                rs = null;
                throw new DBRuntimeException(ex);
            }
        }
    }

    public static void closeResultSetSilently(ResultSet rs) {
        try {
            closeResultSet(rs);
        } catch (Throwable cause) {//获取所有错误，防止抛出运行时异常            
            //Todo:增加日志输出
        }
    }

    public static void closePreparedStatement(PreparedStatement pst) throws DBRuntimeException{
        if (pst != null) {
            try {
                if (!pst.isClosed()) {
                    pst.close();
                }
            } catch (SQLException ex) {
                pst = null;
                throw new DBRuntimeException(ex);
            }
        }
    }

    public static void closePreparedStatementSilently(PreparedStatement pst) {
        try {
            closePreparedStatement(pst);
        } catch (Throwable cause) {//获取所有错误，防止抛出运行时异常            
            //Todo:增加日志输出
        }

    }

    public static void closeConnection(Connection conn) throws DBRuntimeException {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                conn = null;
                throw new DBRuntimeException(ex);
            }
        }
    }

    public static void closeConnectionSilently (Connection conn) {
        try {
            closeConnection(conn);
        } catch (Throwable cause) {//获取所有错误，防止抛出运行时异常            
            //Todo:增加日志输出
        }
    }
    
    public static void close(ResultSet rs, PreparedStatement pst, Connection conn) {
        try {
            closeResultSet(rs);
        } finally {
            try{
                closePreparedStatement(pst);
            } finally {
                closeConnection(conn);
            }
        }                        
    }
    /**
     * 静默关闭结果集、预编译预计和数据库连接。内部没有直接调用各个Silently后缀方法是考虑到这些
     * 方法的日志输出也可能抛出异常，因此需要多个try-catch-finally块连续关闭，即使中间抛出异常
     * 也能继续关闭下个资源。
     * 
     * @param rs
     *      需要关闭的数据源
     * @param pst
     *      需要关闭的预编译语句
     * @param conn 
     *      需要关闭的数据库连接
     */
    public static void closeSilently(ResultSet rs, PreparedStatement pst, Connection conn) {
        try {
            closeResultSet(rs);
        }catch(Throwable cause) {
            //Todo:增加日志输出
        }finally {
            try {
                closePreparedStatement(pst);
            }catch(Throwable cause) {
                //Todo:增加日志输出
            }finally {
                try {
                    closeConnection(conn);
                }catch(Throwable cause) {
                    //Todo:增加日志输出
                }
            }
        }
        
    }
}
