/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.constants;

import com.ripple.engine.db.DatabaseManager;
import com.ripple.engine.models.DeductionModel;
import com.ripple.engine.services.ConstantsService;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Ripple
 */
public class Kernel {

    public static ConstantsService constantsService;
    public static DatabaseManager dbManager;

    private static final ConcurrentLinkedQueue<DeductionModel> deductionQueue
            = new ConcurrentLinkedQueue<DeductionModel>();

    public static void addToQueue(DeductionModel model) {
        deductionQueue.add(model);
    }

    public static DeductionModel dequeue() {
        return deductionQueue.poll();
    }
//
//    public static ConstantsService getConstantsService() {
//        return constantsService;
//    }

}
