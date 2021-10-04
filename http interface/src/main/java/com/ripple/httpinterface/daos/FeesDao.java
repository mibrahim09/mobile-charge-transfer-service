/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.daos;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ripple
 */
@Repository
public class FeesDao extends JdbcDaoSupport {

    @Autowired
    public FeesDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public void addToFeesBalance(double added) {
        this.getJdbcTemplate().update(
                "UPDATE fees_manager SET fees_collected = fees_collected + ?",
                new Object[]{added});
    }

}
