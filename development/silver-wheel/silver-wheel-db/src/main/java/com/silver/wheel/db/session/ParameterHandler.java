/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silver.wheel.db.session;

import java.sql.PreparedStatement;

/**
 *
 * @author Liaojian
 */
public interface ParameterHandler {
    public void setParameter(PreparedStatement ps, Object[] params);
}
