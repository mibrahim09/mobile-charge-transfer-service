/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.services;

import com.ripple.engine.constants.Constants;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Ripple
 */
@Configurable
public class ConstantsService {

    private static final Logger logger = LogManager.getLogger(ConstantsService.class);
    
    @Autowired
    private JdbcTemplate configurationJdbcTemplate;

    public void loadDbConstants() {
        try {
            String SQL = "SELECT * FROM configuration";

            configurationJdbcTemplate.query(SQL, (rs) -> {
                Constants.Statics.TerminateAllThreads = rs.getBoolean("shutdown");
                Constants.Statics.HttpServicelink = rs.getString("http_interface_url");
                Constants.Statics.SELECTION_THREADPOOL_SIZE = rs.getInt("selection_threadpool_size");
                Constants.Statics.SELECTION_THREADPOOL_SIZE = rs.getInt("selection_threadpool_size");
                Constants.Statics.HTTPSENDER_THREADPOOL_SIZE = rs.getInt("httpsender_threadpool_size");
                Constants.Statics.ChunkSize = rs.getInt("selection_chunksize");

                Constants.Statics.SelectionThreadCooldown = rs.getInt("selection_thread_cooldown");
                Constants.Statics.HandlerThreadCooldown = rs.getInt("handler_thread_cooldown");
                Constants.Statics.MonitoringThreadCooldown = rs.getInt("monitoring_thread_cooldown");
//                logger.info("Cfg reload.");
            });

            if (Constants.Statics.TerminateAllThreads) {
                System.out.println("Shutdown.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Bean
    public void loadPropertiesConfig() {
//        Constants.Statics.DB_URL = props.getDbUrl();
//        Constants.Statics.DB_USER = props.getDbUser();
//        Constants.Statics.DB_PASS = props.getDbPassword();

    }
}
