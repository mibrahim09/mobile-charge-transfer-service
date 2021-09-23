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

        /* BEST I HAVE REACH
        
                SELECTION_THREADPOOL_SIZE = 5,
                HANDLER_THREADPOOL_SIZE = 150,
                ChunkSize = 100;
         */
        public static int SELECTION_THREADPOOL_SIZE = 10,
                HANDLER_THREADPOOL_SIZE = 15,//30,
                ChunkSize = 100;
        public static boolean TerminateAllThreads = false;

        public String BROKER_URL = "tcp://localhost:61616",
                BROKER_USERNAME = "admin",
                BROKER_PASSWORD = "admin";
        public static String HttpServicelink = "http://localhost:82/validate/";
        public static String DB_URL = "jdbc:postgresql://localhost/mobile";
        public static String DB_USER = "postgres";
        public static String DB_PASS = "duke12345";

    }

    public static class Defines {

        public static final int SelectionThreadCooldown = 1,
                HandlerThreadCooldown = 1,
                MonitoringThreadCooldown = 2000;// In milliseconds

        public static final String OK = "OK",
                DEDUCTION_QUEUE = "DEDUCTION_QUEUE";

    }
}
