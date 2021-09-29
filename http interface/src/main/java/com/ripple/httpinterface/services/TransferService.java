/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.services;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.daos.addBalance;
import com.ripple.httpinterface.entities.Customer;
import com.ripple.httpinterface.enums.Enums;
import com.ripple.httpinterface.enums.Enums.RejectionStatusCodes;
import com.ripple.httpinterface.models.TransferResponseModel;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ripple
 */
@Service
public class TransferService {

    @Autowired
    public addBalance transferDao;

    @Autowired
    private JdbcTemplate configurationJdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, timeout = -1, readOnly = false)
    private TransferResponseModel doTranfer(Long fromMsdn,
            Long toMsdn,
            Long requestId,
            double transferAmount) {

        TransferResponseModel model = new TransferResponseModel();
        model.setRequestId(requestId);

        // Check if the sender is the receiver.
        if (fromMsdn == toMsdn) {
            model.setRejectionStatusCode(RejectionStatusCodes.SameMsdn);
            return model;
        }
        List<Customer> customers = transferDao.getCustomers(fromMsdn, toMsdn);
        if (customers.size() != 2) {
            // one of both the entities doesnt exist.
            model.setRejectionStatusCode(RejectionStatusCodes.InvalidMsdns);
            return model;
        }

        Customer sender = customers.stream()
                .filter(e -> e.getMsdn() == fromMsdn)
                .findFirst()
                .orElse(null);

        Customer receiver = customers.stream()
                .filter(e -> e.getMsdn() == toMsdn)
                .findFirst()
                .orElse(null);

        model.setReceiverId(toMsdn);
        model.setSenderId(fromMsdn);
        model.setReceiverPreviousAmount(receiver.getBalance());
        model.setSenderPreviousAmount(sender.getBalance());
        model.setTransferAmount(transferAmount);

        double fees = Constants.Statics.Fees;// Get the fees in-case it changes

        if (sender.getBalance() < transferAmount + fees) {
            model.setRejectionStatusCode(RejectionStatusCodes.NotEnoughBalance);
            return model;
        }

        if (transferDao.updateBalance(toMsdn, transferAmount)) {

            double deductedBalance = -(transferAmount + fees);
            transferDao.updateBalance(fromMsdn, deductedBalance);
            model.setTransactionTimeStamp(LocalDateTime.now());
            model.setReceiverNewAmount(receiver.getBalance() + transferAmount);
            model.setSenderNewAmount(sender.getBalance() + deductedBalance);
            model.setRejectionStatusCode(RejectionStatusCodes.None);
            return model;
        }

        model.setRejectionStatusCode(RejectionStatusCodes.NotEnoughBalance);
        return model;
    }

    public TransferResponseModel transferFunds(Long fromMsdn, Long toMsdn, Long requestId, double transferAmount) {
        try {
            TransferResponseModel model = doTranfer(fromMsdn, toMsdn, requestId, transferAmount);
            Enums.StatusCodes statusCode;
            
            switch (model.getRejectionStatusCode()) {
                case None:
                    statusCode = Enums.StatusCodes.Success;
                    break;
                case NotEnoughBalance:
                    statusCode = Enums.StatusCodes.Rejected;
                    break;
                default:
                    statusCode = Enums.StatusCodes.Failed;
                    break;
            }
            
            model.setStatusCode(statusCode);
            return model;

        } catch (Exception e) {
            return new TransferResponseModel(RejectionStatusCodes.Exception);

        }
    }

}
