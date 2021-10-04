/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.controllers;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.enums.Enums;
import com.ripple.httpinterface.models.DeductionModel;
import com.ripple.httpinterface.models.TransferResponseModel;
import com.ripple.httpinterface.services.TransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ripple
 */
@RestController
@RequestMapping("/validate")
public class DeductionProcessorController {

    private static final Logger logger = LogManager.getLogger(DeductionProcessorController.class);
    @Autowired
    private TransferService transferService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity deductFromCustomer(@RequestBody DeductionModel deductionModel) {
        try {

            long start = System.currentTimeMillis();
            TransferResponseModel response = transferService.transferFunds(Long.parseLong(deductionModel.getSenderId()),
                    Long.parseLong(deductionModel.getReceiverId()),
                    deductionModel.getRequestId(),
                    deductionModel.getAmount());

            long total = System.currentTimeMillis() - start;
            logger.info(response.toString() + ",elapsed:" + total);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // TODO: LOG EXCEPTION
            logger.error(e);
            return new ResponseEntity(Constants.Defines.EXC,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
