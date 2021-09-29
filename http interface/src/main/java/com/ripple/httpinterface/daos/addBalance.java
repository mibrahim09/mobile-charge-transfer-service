/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.daos;

import com.ripple.httpinterface.entities.Customer;
import com.ripple.httpinterface.mappers.CustomerMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ripple
 */
@Repository
public class addBalance {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> getCustomers(Long fromMsdn, Long toMsdn) {
        String SQL = "SELECT * FROM users WHERE users.msdn = ? OR users.msdn = ?";
        return jdbcTemplate.query(SQL,
                new Object[]{fromMsdn, toMsdn},
                new CustomerMapper());

    }

    public boolean updateBalance(Long fromMsdn, double d) {
        String sql = "UPDATE USERS SET balance = balance + ? WHERE msdn = ? AND balance - ? > 0";
        return jdbcTemplate.update(sql, new Object[]{d, fromMsdn, d}) != 0;
    }

}
