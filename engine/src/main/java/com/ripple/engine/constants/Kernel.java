/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.constants;

import com.ripple.engine.models.DeductionModel;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Ripple
 */
public class Kernel {

    private static final ConcurrentLinkedQueue<DeductionModel> deductionQueue
            = new ConcurrentLinkedQueue<DeductionModel>();

    public static void addToQueue(DeductionModel model) {
        deductionQueue.add(model);
    }

    public static DeductionModel dequeue() {
        return deductionQueue.poll();
    }

}
