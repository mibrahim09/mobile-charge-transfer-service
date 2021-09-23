/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Ripple
 */
@Data
@AllArgsConstructor
public class DeductionModel implements Serializable {
    private int requestId;
    private String senderId;
    private String receiverId;
    private double amount;

}
