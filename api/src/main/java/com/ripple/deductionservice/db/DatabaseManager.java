/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.deductionservice.db;

import com.ripple.deductionservice.constants.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ripple
 */
@Component
public class DatabaseManager {


    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(Constants.Statics.DB_URL,
                Constants.Statics.DB_USER,
                Constants.Statics.DB_PASS);
    }
    
    
    @Autowired
    private JdbcTemplate configurationJdbcTemplate;

    public JdbcTemplate getConnection() {
        return configurationJdbcTemplate;
    }


}
