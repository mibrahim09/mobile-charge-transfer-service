/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.constants;

/**
 *
 * @author Ripple
 */
public class Constants {

    public static class Statics {

        public static boolean TerminateAllThreads = false;
        public static int RECEIVER_THREADPOOL_SIZE,
                ReceiverThreadCooldown,
                MonitoringThreadCooldown,
                MAX_PER_BATCH;
        
        public static float FEES_PERCENT = 0.0f;
    }

    public static class Defines {

        public static final String OK = "OK",
                EXC = "ERROR",
                DEDUCTION_QUEUE = "DEDUCTION_QUEUE",
                DbUrlKey = "${spring.datasource.url}",
                DbUserKey = "${spring.datasource.username}",
                DbPassKey = "${spring.datasource.password}",
                DbDriverKey = "${spring.datasource.driver-class-name}",
                DbTimeout = "${spring.datasource.idle-timeout}",
                DbMaxpoolsize = "${spring.datasource.maximumPoolSize}",
                DbMinIdle = "${spring.datasource.minimumIdle}",
                PropertiesFileName = "application.properties";

    }
}
