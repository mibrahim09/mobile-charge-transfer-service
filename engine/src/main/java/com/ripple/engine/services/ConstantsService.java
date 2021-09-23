/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.services;

import com.ripple.engine.constants.Constants;
import com.ripple.engine.db.DatabaseManager;
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
                    Constants.Statics.HttpServicelink = rs.getString("http_interface_url");
                    Constants.Statics.SELECTION_THREADPOOL_SIZE = rs.getInt("selection_threadpool_size");
                    Constants.Statics.SELECTION_THREADPOOL_SIZE = rs.getInt("selection_threadpool_size");
                    Constants.Statics.HTTPSENDER_THREADPOOL_SIZE = rs.getInt("httpsender_threadpool_size");
                    Constants.Statics.ChunkSize = rs.getInt("selection_chunksize");

                    Constants.Statics.SelectionThreadCooldown = rs.getInt("selection_thread_cooldown");
                    Constants.Statics.HandlerThreadCooldown = rs.getInt("handler_thread_cooldown");
                    Constants.Statics.MonitoringThreadCooldown = rs.getInt("monitoring_thread_cooldown");

                }
                if (Constants.Statics.TerminateAllThreads) {
                    System.out.println("Shutdown.");
                }
            }
        } catch (Exception e) {
        }
    }
}
