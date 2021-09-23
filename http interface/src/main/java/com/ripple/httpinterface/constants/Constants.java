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
        public static int HANDLER_THREADPOOL_SIZE = 15;//50

        public String BROKER_URL = "tcp://localhost:61616",
                BROKER_USERNAME = "admin",
                BROKER_PASSWORD = "admin";
        public static String DB_URL = "jdbc:postgresql://localhost/mobile";
        public static String DB_USER = "postgres";
        public static String DB_PASS = "duke12345";

    }

    public static class Defines {

        public static final int HandlerThreadCooldown = 1,
                MAX_PER_BATCH = 250;// In milliseconds

        public static final String OK = "OK",
                EXC = "ERROR",
                DEDUCTION_QUEUE = "DEDUCTION_QUEUE";

    }
}
