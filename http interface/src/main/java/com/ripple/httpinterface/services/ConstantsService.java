/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.services;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.db.DatabaseManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Ripple
 */
public class ConstantsService {

    public static void loadConstants() {
        try {
            try ( Connection conn = DatabaseManager.connect()) {
                String SQL = "SELECT * FROM configuration";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                while (rs.next()) {
                    Constants.Statics.TerminateAllThreads = rs.getBoolean("shutdown");
                    Constants.Statics.ReceiverThreadCooldown = rs.getInt("receiver_thread_cooldown");
                    Constants.Statics.MAX_PER_BATCH = rs.getInt("max_per_batch");
                    Constants.Statics.RECEIVER_THREADPOOL_SIZE = rs.getInt("receiver_threadpool_size");

                }
                if (Constants.Statics.TerminateAllThreads) {
                    System.out.println("Shutdown.");
                }
            }
        } catch (Exception e) {
        }
    }
}
