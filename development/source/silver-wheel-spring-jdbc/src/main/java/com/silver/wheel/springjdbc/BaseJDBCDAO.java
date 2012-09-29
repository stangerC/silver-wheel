/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.wheel.springjdbc;

import com.silver.wheel.db.Entity;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author liaojian
 */
public abstract class BaseJDBCDAO extends JdbcDaoSupport{
    public BaseJDBCDAO(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    
    public List<Entity> getEntities(String sql, Object[] args) {
        JdbcTemplate template = getJdbcTemplate();
        return template.query(sql, args, getRowMapper());        
    }
    
    public int updateEntity(String sql, Object[] args) {        
        return getJdbcTemplate().update(sql, args);
    }
    
    protected abstract RowMapper getRowMapper();
}
