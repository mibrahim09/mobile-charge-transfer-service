/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.threads;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ripple
 */
public class ThreadsManager {

    private static final Logger logger = LogManager.getLogger(ThreadsManager.class);
    private static ConcurrentMap<Long, Thread> threadsBag = new ConcurrentHashMap<Long, Thread>();

    public static void onAddThread(Thread thread) {
        threadsBag.put(thread.getId(), thread);
    }

    public static void onKillThread(Thread thread) {

        long threadId = thread.getId();
        if (threadsBag.containsKey(threadId)) {
            threadsBag.remove(threadId);
        }
    }

    public void startThreads() {
        try {
            new MonitoringThread().start();
            logger.debug("Threads are running.");
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
