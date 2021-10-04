/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.constants;

/**
 *
 * @author Ripple
 */
public class Constants {

    public static class Statics {

        public static int SELECTION_THREADPOOL_SIZE,
                HTTPSENDER_THREADPOOL_SIZE,
                ChunkSize,
                SelectionThreadCooldown,
                HandlerThreadCooldown,
                MonitoringThreadCooldown;

        public static boolean TerminateAllThreads;

        public static String HttpServicelink;
    }

    public static class Defines {

        public static final String OK = "OK",
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
