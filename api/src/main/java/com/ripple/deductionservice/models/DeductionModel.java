/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.deductionservice.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Ripple
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionModel implements Serializable {

    @NotNull
    private String senderId;
    @NotNull
    private String receiverId;
    @NotNull
    private double amount;
    @NotNull
    private double fees;

}
