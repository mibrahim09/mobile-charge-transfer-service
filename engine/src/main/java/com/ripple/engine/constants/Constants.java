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

        /* BEST I HAVE REACHED
        
            SELECTION_THREADPOOL_SIZE = 10,
            HTTPSENDER_THREADPOOL_SIZE = 15,
            ChunkSize = 100;
        
         */
        public static int SELECTION_THREADPOOL_SIZE,
                HTTPSENDER_THREADPOOL_SIZE,
                ChunkSize,
                SelectionThreadCooldown,
                HandlerThreadCooldown,
                MonitoringThreadCooldown;
        
        public static boolean TerminateAllThreads;

        public static String HttpServicelink;
        public static String DB_URL = "jdbc:postgresql://localhost/mobile";
        public static String DB_USER = "postgres";
        public static String DB_PASS = "duke12345";

    }

    public static class Defines {

        public static final String OK = "OK",
                DEDUCTION_QUEUE = "DEDUCTION_QUEUE";

    }
}
