/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Ripple
 */
@AllArgsConstructor
@Data
public class Customer {

    Long msdn;
    String name;
    String nationalId;
    double balance;
}
