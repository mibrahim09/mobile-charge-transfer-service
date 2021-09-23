/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.threads;

import com.ripple.engine.constants.Constants;
import com.ripple.engine.services.ConstantsService;

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
        ConstantsService.loadConstants();
    }
}
