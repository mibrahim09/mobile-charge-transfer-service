/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.threads;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.constants.Kernel;

/**
 *
 * @author Ripple
 */
public class MonitoringThread extends TimerThread {


    public MonitoringThread() {
        super(Constants.Statics.MonitoringThreadCooldown);
    }

    @Override
    public void threadAction() {
        Kernel.constantsService.loadConstants();
    }
}
