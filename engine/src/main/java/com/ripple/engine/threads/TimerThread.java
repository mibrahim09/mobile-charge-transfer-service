/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.threads;

import com.ripple.engine.constants.Constants;

/**
 *
 * @author Ripple
 */
public abstract class TimerThread extends Thread {

    int cooldown;

    TimerThread(int cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void run() {
        ThreadsManager.onAddThread(Thread.currentThread());

        while (!Constants.Statics.TerminateAllThreads) {
            try {
                threadAction();
                Thread.sleep(this.cooldown);
            } catch (Exception e) {
            }
        }
        ThreadsManager.onKillThread(Thread.currentThread());
    }

    public abstract void threadAction();

}
