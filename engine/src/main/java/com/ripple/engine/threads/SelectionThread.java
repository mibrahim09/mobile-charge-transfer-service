/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.threads;

import com.ripple.engine.constants.Constants;
import com.ripple.engine.constants.Kernel;
import com.ripple.engine.db.DatabaseManager;
import com.ripple.engine.models.DeductionModel;
import java.net.http.HttpClient;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Ripple
 */
public class SelectionThread extends TimerThread {

    public SelectionThread() {
        super(Constants.Statics.SelectionThreadCooldown);
    }

    @Override
    public void threadAction() {
        String SQL = "UPDATE requests T1 SET processed = TRUE FROM ( SELECT * FROM requests T3 INNER JOIN channels T2 ON ( T3.source_id = T2.channel_id ) WHERE T3.processed = FALSE ORDER BY T2.priority DESC, T3.requested_timestamp ASC LIMIT "
                + Constants.Statics.ChunkSize
                + " FOR UPDATE ) AS subquery WHERE T1.request_id = subquery.request_id "
                + "RETURNING T1.sender_id, T1.receiver_id, T1.transfer_amount, T1.source_id, T1.request_id";
        Connection conn;
        try {
            conn = DatabaseManager.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            conn.close();
            HttpClient client = null;
            while (rs.next()) {
                DeductionModel model = new DeductionModel(rs.getString("sender_id"),
                        rs.getString("receiver_id"),
                        rs.getDouble("transfer_amount"),
                        rs.getString("request_id"));

                Kernel.addToQueue(model);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
//            Logger.getLogger(SelectionThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
