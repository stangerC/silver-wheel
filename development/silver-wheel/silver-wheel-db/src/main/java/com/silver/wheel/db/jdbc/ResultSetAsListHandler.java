package com.silver.wheel.db.jdbc;

import com.silver.wheel.db.exception.DBRuntimeException;
import com.silver.wheel.db.mapper.RowToEntityMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Liaojian
 */
public class ResultSetAsListHandler<E> implements ResultSetHandler<List<E>>{
    private RowToEntityMapper<E> mapper;
    
    public ResultSetAsListHandler(RowToEntityMapper mapper) {
        this.mapper = mapper;
    }
        
    public List<E> handle(ResultSet rs) {
        List<E> result = new ArrayList<E>();
        try {
            while(rs.next()) {
                result.add(mapper.mapping(rs));
            }
        } catch (SQLException ex) {
            throw new DBRuntimeException(ex);
        }
        
        return result;
    }
    
}
