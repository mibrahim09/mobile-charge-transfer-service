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
                MAX_PER_BATCH;

        public static String DB_URL = "jdbc:postgresql://localhost/mobile";
        public static String DB_USER = "postgres";
        public static String DB_PASS = "duke12345";

    }

    public static class Defines {

        public static final String OK = "OK",
                EXC = "ERROR",
                DEDUCTION_QUEUE = "DEDUCTION_QUEUE";

    }
}
