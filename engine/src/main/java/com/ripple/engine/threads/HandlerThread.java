/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.threads;

import com.ripple.engine.constants.Constants;
import com.ripple.engine.constants.Kernel;
import com.ripple.engine.models.DeductionModel;
import com.ripple.engine.models.TransferResponseModel;

import java.net.http.HttpClient;
import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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

            while (model != null) {

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<DeductionModel> request = new HttpEntity<>(model);
                long timeStamp = System.currentTimeMillis();
                TransferResponseModel response = restTemplate.postForObject(Constants.Statics.HttpServicelink,
                        request,
                        TransferResponseModel.class);
                long elapsed = System.currentTimeMillis() - timeStamp;

                timeStamp = System.currentTimeMillis();

                JdbcTemplate template = Kernel.dbManager.getConnection();
                String sql = "insert into requests_history values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                template.update(sql, new Object[]{
                    response.getRequestId(),
                    0,
                    response.getSenderPreviousAmount(),
                    response.getSenderNewAmount(),
                    response.getTransferAmount(),
                    response.getReceiverPreviousAmount(),
                    response.getReceiverNewAmount(),
                    0,
                    0,
                    response.getStatusCode().ordinal(),
                    response.getTransactionTimeStamp(),
                    LocalDateTime.now(),// Needs to be edited just a dummy val for now
                    response.getRejectionStatusCode().ordinal()});

                long dbElapsed = System.currentTimeMillis() - timeStamp;
                logger.info("Response:" + response.toString() + ",ElapsedHttp" + elapsed + ",ElapsedSql" + dbElapsed);

                model = Kernel.dequeue();
            }
        } catch (Exception e) {
            logger.error(e);
        }

    }
}
