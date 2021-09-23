/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.deductionservice.controllers;

import com.ripple.deductionservice.constants.Constants;
import com.ripple.deductionservice.db.DatabaseManager;
import com.ripple.deductionservice.models.DeductionModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ripple
 */
@RestController
@RequestMapping("/deduct")
public class DeductionSmsController {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity deductFromCustomer(@RequestParam int channelId, @RequestBody DeductionModel deductionModel) {
        try {
            String SQL = "INSERT INTO requests(sender_id,receiver_id,transfer_amount,source_id,requested_timestamp) "
                    + "VALUES(?,?,?,?,?)";

            try ( Connection conn = DatabaseManager.connect()) {
                PreparedStatement cmd = conn.prepareStatement(SQL);

                cmd.setString(1, deductionModel.getSenderId());
                cmd.setString(2, deductionModel.getReceiverId());
                cmd.setDouble(3, deductionModel.getAmount());
                cmd.setInt(4, channelId);
                cmd.setObject(5, LocalDateTime.now());
                cmd.executeUpdate();
            }
            return new ResponseEntity(Constants.Defines.OK,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            // TODO: LOG EXCEPTION
            System.out.print(e);
            return new ResponseEntity(Constants.Defines.EXC,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}