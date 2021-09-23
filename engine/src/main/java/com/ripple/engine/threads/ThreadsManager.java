/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.threads;

import com.ripple.engine.constants.Constants;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author Ripple
 */
public class ThreadsManager {

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

    public static void startThreads() {
        try {
            for (int i = 0; i < Constants.Statics.HANDLER_THREADPOOL_SIZE; i++) {
                TimerThread thread = new HandlerThread();
                thread.start();
            }

            for (int i = 0; i < Constants.Statics.SELECTION_THREADPOOL_SIZE; i++) {
                TimerThread thread = new SelectionThread();
                thread.start();
            }

            System.out.println("ALL THREADS UP AND RUNNING!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
