/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.mappers;

import com.ripple.engine.models.DeductionModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ripple
 */
public class DeductionMapper implements RowMapper<DeductionModel> {

    public DeductionModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        DeductionModel model = new DeductionModel();
        model.setSenderId(rs.getString("sender_id"));
        model.setReceiverId(rs.getString("receiver_id"));
        model.setAmount(rs.getDouble("transfer_amount"));
        model.setRequestId(rs.getLong("request_id"));
        return model;
    }
}
