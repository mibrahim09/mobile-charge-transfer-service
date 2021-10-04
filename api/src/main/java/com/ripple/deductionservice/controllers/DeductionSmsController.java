/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.deductionservice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ripple.deductionservice.constants.Constants;
import com.ripple.deductionservice.models.DeductionModel;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ripple.deductionservice.enums.Enums;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ripple
 */
@RestController
@RequestMapping("/deduct")
public class DeductionSmsController {

    private static final Logger logger = LogManager.getLogger(DeductionSmsController.class);

    @Autowired
    private JdbcTemplate configurationJdbcTemplate;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity deductFromCustomer(@RequestParam int channelId, @RequestBody DeductionModel deductionModel) {
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            if (Constants.Statics.ShutdownFlag) { 
                /* 
                    Shutdown flag was not mentioned in the requirements regarding this service.
                    So i assumed there is another shutdown flag that'll be set from the database too but not related to the one in the configuration.
                    This is just a dummy test but always set to true for now till we figure where it needs to be set.
                */
                Enums.StatusCodes statusCode = Enums.StatusCodes.Rejected;
                Enums.RejectionStatusCodes rejectionCode = Enums.RejectionStatusCodes.ServerDisabled;

                logger.info("DeductCtrl,Flag:" + statusCode
                        + ",RejectionCode:" + rejectionCode
                        + ",Sender" + deductionModel.getSenderId()
                        + ",Recv" + deductionModel.getReceiverId()
                        + ",Amnt:" + deductionModel.getAmount()
                        + ",Channel:" + channelId);

                response.put(Constants.Defines.StatusCode, statusCode);
                response.put(Constants.Defines.RejectionCode, rejectionCode);
                return ResponseEntity.badRequest().body(response);
            }

            String SQL = "INSERT INTO requests(sender_id,receiver_id,transfer_amount,source_id,requested_timestamp) "
                    + "VALUES(?,?,?,?,?)";

            configurationJdbcTemplate.update(SQL,
                    deductionModel.getSenderId(),
                    deductionModel.getReceiverId(),
                    deductionModel.getAmount(),
                    channelId,
                    LocalDateTime.now());

            Enums.StatusCodes statusCode = Enums.StatusCodes.Success;
            logger.info("DeductCtrl,Flag:" + statusCode
                    + ",Sender" + deductionModel.getSenderId()
                    + ",Recv" + deductionModel.getReceiverId()
                    + ",Amnt:" + deductionModel.getAmount()
                    + ",Channel:" + channelId);

            response.put(Constants.Defines.StatusCode, statusCode);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Enums.StatusCodes statusCode = Enums.StatusCodes.Failed;
            Enums.RejectionStatusCodes rejectionCode = Enums.RejectionStatusCodes.Exception;

            logger.info("DeductCtrl,Flag:" + statusCode
                    + ",RejectionCode:" + rejectionCode
                    + ",Sender" + deductionModel.getSenderId()
                    + ",Recv" + deductionModel.getReceiverId()
                    + ",Amnt:" + deductionModel.getAmount()
                    + ",Channel:" + channelId);
            logger.error("DeductCtrl," + e);

            response.put(Constants.Defines.StatusCode, statusCode);
            response.put(Constants.Defines.RejectionCode, rejectionCode);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
