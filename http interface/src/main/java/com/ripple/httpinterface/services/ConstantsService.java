/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.services;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.db.DatabaseManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Ripple
 */
public class ConstantsService {

    private static final Logger logger = LogManager.getLogger(ConstantsService.class);

    @Autowired
    private JdbcTemplate configurationJdbcTemplate;

    public void loadConstants() {
        try {
            String SQL = "SELECT * FROM configuration";

            configurationJdbcTemplate.query(SQL, (rs) -> {
                Constants.Statics.TerminateAllThreads = rs.getBoolean("shutdown");
                Constants.Statics.ReceiverThreadCooldown = rs.getInt("receiver_thread_cooldown");
                Constants.Statics.MAX_PER_BATCH = rs.getInt("max_per_batch");
                Constants.Statics.RECEIVER_THREADPOOL_SIZE = rs.getInt("receiver_threadpool_size");

            });
            if (Constants.Statics.TerminateAllThreads) {
                System.out.println("Shutdown.");
            }
        } catch (Exception e) {
        }
    }
}
