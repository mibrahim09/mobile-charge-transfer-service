/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.services;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.daos.FeesDao;
import com.ripple.httpinterface.daos.TransferDao;
import com.ripple.httpinterface.enums.Enums;
import com.ripple.httpinterface.enums.Enums.RejectionStatusCodes;
import com.ripple.httpinterface.models.TransferResponseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ripple
 */
@Service
public class TransferService {

    private static final Logger logger = LogManager.getLogger(TransferService.class);

    @Autowired
    public TransferDao transferDao;

    @Autowired
    public FeesDao feesDao;

    public TransferResponseModel transferFunds(Long fromMsdn, Long toMsdn, Long requestId, double transferAmount) {
        try {
            TransferResponseModel model = transferDao.doTranfer(fromMsdn, toMsdn, requestId, transferAmount);
            model.setFeesAfter(Constants.Statics.FEES_PERCENT);
            Enums.StatusCodes statusCode;
            
            switch (model.getRejectionStatus()) {
                case OK:
                    feesDao.addToFeesBalance(model.getFees());
                    statusCode = Enums.StatusCodes.Success;
                    break;
                case NotEnoughBalance:
                    statusCode = Enums.StatusCodes.Rejected;
                    break;
                default:
                    statusCode = Enums.StatusCodes.Failed;
                    break;
            }
            
            model.setStatusCode(statusCode.ordinal());
            return model;

        } catch (TxException e) {
            return e.getModel();

        } catch (Exception e) {
            logger.error(e);
            return new TransferResponseModel(RejectionStatusCodes.Exception);
        }
    }


}
