/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.daos;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.entities.Customer;
import com.ripple.httpinterface.enums.Enums;
import com.ripple.httpinterface.mappers.CustomerMapper;
import com.ripple.httpinterface.models.TransferResponseModel;
import com.ripple.httpinterface.services.TxException;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ripple
 */
@Repository
@Transactional
public class TransferDao extends JdbcDaoSupport {

    @Autowired
    public TransferDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<Customer> getCustomers(Long fromMsdn, Long toMsdn) {
        String SQL = "SELECT * FROM users WHERE users.msdn = ? OR users.msdn = ? FOR UPDATE";
        return this.getJdbcTemplate().query(SQL,
                new Object[]{fromMsdn, toMsdn},
                new CustomerMapper());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public TransferResponseModel doTranfer(Long fromMsdn,
            Long toMsdn,
            Long requestId,
            double transferAmount) throws TxException {
        
        TransferResponseModel model = new TransferResponseModel();
        model.setFeesBefore(Constants.Statics.FEES_PERCENT);
        model.setRequestId(requestId);

        // Check if the sender is the receiver.
        if (fromMsdn == toMsdn) {
            model.setRejection(Enums.RejectionStatusCodes.SameMsdn);
            return model;
        }
        List<Customer> customers = getCustomers(fromMsdn, toMsdn);
        if (customers.size() != 2) {
            // one of both the entities doesnt exist.
            model.setRejection(Enums.RejectionStatusCodes.InvalidMsdns);
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

        double fees = (transferAmount * model.getFeesBefore()) / 100.0;// Get the fees in-case it changes
        
        if (sender.getBalance() < transferAmount + fees) {
            model.setRejection(Enums.RejectionStatusCodes.NotEnoughBalance);
            throw new TxException(model);
        }

        double deductedBalance = -(transferAmount + fees);
        
        addBalance(fromMsdn, deductedBalance);
        addBalance(toMsdn, transferAmount);
        model.setFees(fees);
        model.setTransactionTimeStamp(LocalDateTime.now());
        model.setReceiverNewAmount(receiver.getBalance() + transferAmount);
        model.setSenderNewAmount(sender.getBalance() + deductedBalance);
        model.setRejection(Enums.RejectionStatusCodes.OK);
        return model;

    }

    @Transactional(propagation = Propagation.MANDATORY)
    public boolean addBalance(Long fromMsdn, double d) throws RuntimeException {
        String sql = "UPDATE USERS SET balance = balance + ? WHERE msdn = ?";// AND balance + ? > 0";
        this.getJdbcTemplate().update(sql, new Object[]{d, fromMsdn});// != 0;
//        if (sql != "") {
//            throw new RuntimeException("TESTING");
//        }
        return true;
    }

}
