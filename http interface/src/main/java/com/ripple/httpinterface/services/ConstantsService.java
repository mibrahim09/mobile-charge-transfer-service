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

            configurationJdbcTemplate.query("SELECT * FROM configuration", (rs) -> {
                Constants.Statics.TerminateAllThreads = rs.getBoolean("shutdown");
                Constants.Statics.MonitoringThreadCooldown = rs.getInt("monitoring_thread_cooldown");

            });

            configurationJdbcTemplate.query("SELECT fees_percent FROM fees_manager", (rs) -> {
                Constants.Statics.FEES_PERCENT = rs.getFloat("fees_percent");
            });

            if (Constants.Statics.TerminateAllThreads) {
                logger.debug("Shutdown flag triggered.");
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
