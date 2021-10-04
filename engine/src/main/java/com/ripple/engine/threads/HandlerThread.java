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

import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.ResourceAccessException;
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
        DeductionModel model = null;
        try {
            model = Kernel.dequeue();

            while (model != null) {

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<DeductionModel> request = new HttpEntity<>(model);
                long timeStamp = System.currentTimeMillis();
                TransferResponseModel response = restTemplate.postForObject(Constants.Statics.HttpServicelink,
                        request,
                        TransferResponseModel.class);
                long elapsed = System.currentTimeMillis() - timeStamp;

                timeStamp = System.currentTimeMillis();

                if (response.getRequestId() != null) {

                    JdbcTemplate template = Kernel.dbManager.getConnection();
                    String sql = "insert into requests_history values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    template.update(sql, new Object[]{
                        response.getRequestId(),
                        response.getTransferAmount() + response.getFees(),
                        response.getSenderPreviousAmount(),
                        response.getSenderNewAmount(),
                        response.getTransferAmount(),
                        response.getReceiverPreviousAmount(),
                        response.getReceiverNewAmount(),
                        response.getFees(),
                        response.getFeesBefore(),
                        response.getFeesAfter(),
                        response.getStatusCode(),
                        response.getTransactionTimeStamp(),
                        response.getRejectionStatusCode()});
                }
                long dbElapsed = System.currentTimeMillis() - timeStamp;
                logger.info("Response:" + response.toString() + ",ElapsedHttp" + elapsed + ",ElapsedSql" + dbElapsed);
                model = Kernel.dequeue();
            }
        } catch (ResourceAccessException ex) {
            // Http interface is down.
            if (model != null) {
                logger.error("Unhandled:" + model.toString());
                // We can also implement here a SQL log in the db or set processed = false in the database.
            }
        } catch (Exception e) {
            logger.error(e);
        }

    }
}
