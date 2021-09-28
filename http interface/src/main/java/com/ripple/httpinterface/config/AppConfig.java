/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.config;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.db.DatabaseManager;
import com.ripple.httpinterface.services.ConstantsService;
import com.ripple.httpinterface.threads.ThreadsManager;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Ripple
 */
@Configuration
public class AppConfig {

    @Bean
    public ConstantsService constantsSVR() {
        return new ConstantsService();
    }

    @Bean
    public ThreadsManager threadsMgr() {
        return new ThreadsManager();
    }

    @Bean
    public DatabaseManager dbMgr() {
        return new DatabaseManager();
    }

    @Bean(name = "configurationDataSource")
    @ConfigurationProperties("spring.configurationdatasource")
    public HikariDataSource configurationDataSource() {
//        HikariDataSource test = DataSourceBuilder.create().type(HikariDataSource.class).build();
        HikariDataSource test = new HikariDataSource();
        test.setJdbcUrl(Constants.Statics.DB_URL);
        test.setUsername(Constants.Statics.DB_USER);
        test.setPassword(Constants.Statics.DB_PASS);
        test.setDriverClassName("org.postgresql.Driver");
        test.setConnectionTimeout(60000);
        test.setMaximumPoolSize(50);
        test.setMinimumIdle(50);
        return test;
    }

    @Bean(name = "configurationJdbcTemplate")
    @Autowired
    public JdbcTemplate configurationJdbcTemplate(DataSource configurationDataSource) {
        return new JdbcTemplate(configurationDataSource);
    }

}
