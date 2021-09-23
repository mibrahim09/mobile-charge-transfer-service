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
public class MonitoringThread extends TimerThread {

    public MonitoringThread(int cooldown) {
        super(cooldown);
    }

    @Override
    public void threadAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
