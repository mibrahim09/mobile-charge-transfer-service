/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.threads;

import com.ripple.engine.constants.Constants;
import com.ripple.engine.constants.Kernel;
import com.ripple.engine.models.DeductionModel;
import com.ripple.engine.models.DeductionResponseModel;

import java.net.http.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Ripple
 */
public class HandlerThread extends TimerThread {

    public HandlerThread() {
        super(Constants.Statics.HandlerThreadCooldown);
    }
    private static final Logger logger = LogManager.getLogger(HandlerThread.class);

    @Override
    public void threadAction() {
        try {
            DeductionModel model = Kernel.dequeue();
            HttpClient client = null;

            while (model != null) {
                if (client == null) {
                    client = HttpClient.newHttpClient();
                }

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<DeductionModel> request = new HttpEntity<>(model);
                long timeStamp = System.currentTimeMillis();
                DeductionResponseModel response = restTemplate.postForObject(Constants.Statics.HttpServicelink,
                        request,
                        DeductionResponseModel.class);
                long elapsed = System.currentTimeMillis() - timeStamp;
                logger.debug("HdlrThread-Sent " + elapsed);

                model = Kernel.dequeue();
            }
        } catch (Exception e) {
        }

    }
}
