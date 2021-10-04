/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.deductionservice.config;

import com.ripple.deductionservice.constants.Constants;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value(Constants.Defines.DbUrlKey)
    public String DbUrl;
    @Value(Constants.Defines.DbUserKey)
    public String DbUsername;
    @Value(Constants.Defines.DbPassKey)
    public String DbPassword;
    @Value(Constants.Defines.DbDriverKey)
    public String DbDriver;
    @Value(Constants.Defines.DbMaxpoolsize)
    public String DbMaxPoolSize;
    @Value(Constants.Defines.DbMinIdle)
    public String DbMinIdle;
    @Value(Constants.Defines.DbTimeout)
    public String DbTimeOut;

    @Bean
    public HikariDataSource configurationDataSource() {
        HikariDataSource hikariDatasource = new HikariDataSource();
        hikariDatasource.setJdbcUrl(DbUrl);
        hikariDatasource.setUsername(DbUsername);
        hikariDatasource.setPassword(DbPassword);
        hikariDatasource.setDriverClassName(DbDriver);
        hikariDatasource.setConnectionTimeout(Integer.parseInt(DbTimeOut));
        hikariDatasource.setMaximumPoolSize(Integer.parseInt(DbMaxPoolSize));
        hikariDatasource.setMinimumIdle(Integer.parseInt(DbMinIdle));
        return hikariDatasource;
    }

    @Bean
    public JdbcTemplate configurationJdbcTemplate(DataSource configurationDataSource) {
        return new JdbcTemplate(configurationDataSource);
    }

}
