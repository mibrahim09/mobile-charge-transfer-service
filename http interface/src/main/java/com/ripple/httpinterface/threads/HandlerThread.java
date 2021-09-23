/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.threads;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.constants.Kernel;
import com.ripple.httpinterface.db.DatabaseManager;
import com.ripple.httpinterface.models.DeductionModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

/**
 *
 * @author Ripple
 */
public class HandlerThread extends TimerThread {

    public HandlerThread() {
        super(Constants.Defines.HandlerThreadCooldown);
    }

    @Override
    public void threadAction() {
        try {
            DeductionModel model = Kernel.dequeue();
            if (model != null) {
                try ( Connection conn = DatabaseManager.connect()) {
                    String SQL = "INSERT INTO requests_test (request_id, sender_id,receiver_id,transfer_amount, end_timestamp) "
                            + "VALUES(?,?,?,?,?)";
                    PreparedStatement cmd = conn.prepareStatement(SQL);
                    int count = 0;
                    while (model != null) {
                        cmd.setInt(1, model.getRequestId());
                        cmd.setString(2, model.getSenderId());
                        cmd.setString(3, model.getReceiverId());
                        cmd.setDouble(4, model.getAmount());
                        cmd.setObject(5, LocalDateTime.now());
                        cmd.addBatch();
                        model = Kernel.dequeue();
                        count++;
                        if (count >= Constants.Defines.MAX_PER_BATCH) {
                            break;
                        }
                    }
                    cmd.executeBatch();
                }
            }
        } catch (Exception e) {
        }

    }
}
