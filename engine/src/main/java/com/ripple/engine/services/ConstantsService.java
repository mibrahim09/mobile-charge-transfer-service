/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.services;

import com.ripple.engine.constants.Constants;
import com.ripple.engine.db.DatabaseManager;
import com.ripple.engine.models.DeductionModel;
import java.net.http.HttpClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

/**
 *
 * @author Ripple
 */
public class ConstantsService {

    public static void loadConstants() throws SQLException {
        String SQL = "SELECT * FROM configuration";
        try ( Connection conn = DatabaseManager.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                Constants.Statics.TerminateAllThreads = rs.getBoolean("shutdown");
                Constants.Statics.HttpServicelink = rs.getString("http_interface_url");
            }
            if (Constants.Statics.TerminateAllThreads) {
                System.out.println("Shutdown.");
            }
        }
    }
}
