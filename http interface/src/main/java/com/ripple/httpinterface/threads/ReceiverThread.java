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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Ripple
 */
public class ReceiverThread extends TimerThread {

    public ReceiverThread() {
        super(Constants.Statics.ReceiverThreadCooldown);
    }

    @Override
    public void threadAction() {
        try {
            DeductionModel model = Kernel.dequeue();
            if (model != null) {
                JdbcTemplate jdbcTemp = Kernel.dbManager.getConnection();

                List<DeductionModel> listDeduction = new ArrayList();
                int count = 0;
                while (model != null) {
                    listDeduction.add(model);
                    count++;
                    if (count >= Constants.Statics.MAX_PER_BATCH) {
                        break;
                    }
                    model = Kernel.dequeue();
                }
                String SQL = "INSERT INTO requests_test (request_id, sender_id,receiver_id,transfer_amount, end_timestamp) "
                        + "VALUES(?,?,?,?,?)";
                jdbcTemp.batchUpdate(SQL, new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, listDeduction.get(i).getRequestId());
                        ps.setString(2, listDeduction.get(i).getSenderId());
                        ps.setString(3, listDeduction.get(i).getSenderId());
                        ps.setDouble(4, listDeduction.get(i).getAmount());
                        ps.setObject(5, LocalDateTime.now());
                    }

                    public int getBatchSize() {
                        return listDeduction.size();
                    }

                });
//                try ( Connection conn = DatabaseManager.connect()) {
//
//                    PreparedStatement cmd = conn.prepareStatement(SQL);
//                    int count = 0;
//                    while (model != null) {
//                        cmd.setInt(1, model.getRequestId());
//                        cmd.setString(2, model.getSenderId());
//                        cmd.setString(3, model.getReceiverId());
//                        cmd.setDouble(4, model.getAmount());
//                        cmd.setObject(5, LocalDateTime.now());
//                        cmd.addBatch();
//                        model = Kernel.dequeue();
//                        count++;
//                        if (count >= Constants.Statics.MAX_PER_BATCH) {
//                            break;
//                        }
//                    }
//                    cmd.executeBatch();
//                }
            }
        } catch (Exception e) {
        }

    }
}
