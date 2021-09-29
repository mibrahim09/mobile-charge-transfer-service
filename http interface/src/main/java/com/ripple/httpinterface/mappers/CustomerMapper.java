/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.mappers;

/**
 *
 * @author Ripple
 */
import com.ripple.httpinterface.entities.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Customer(rs.getLong("msdn"),
                rs.getString("name"),
                rs.getString("national_id"),
                rs.getFloat("balance"));

    }
}
