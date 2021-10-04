/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.deductionservice.constants;

/**
 *
 * @author Ripple
 */
public class Constants {

    public static class Statics {

        public static String DB_URL = "jdbc:postgresql://localhost/mobile";
        public static String DB_USER = "postgres";
        public static String DB_PASS = "duke12345";
        public static boolean ShutdownFlag = false;
    }

    public static class Defines {

        public final static String OK = "OK",
                EXC = "ERROR",
                DEDUCTION_QUEUE = "DEDUCTION_QUEUE",
                DbUrlKey = "${spring.datasource.url}",
                DbUserKey = "${spring.datasource.username}",
                DbPassKey = "${spring.datasource.password}",
                DbDriverKey = "${spring.datasource.driver-class-name}",
                DbTimeout = "${spring.datasource.idle-timeout}",
                DbMaxpoolsize = "${spring.datasource.maximumPoolSize}",
                DbMinIdle = "${spring.datasource.minimumIdle}",
                PropertiesFileName = "application.properties",
                StatusCode = "StatusCode",
                RejectionCode = "RejectionCode";

    }
}
