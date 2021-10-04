/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.services;

import com.ripple.httpinterface.models.TransferResponseModel;

/**
 *
 * @author Ripple
 */
public class TxException extends Exception {

    private static final long serialVersionUID = -3128681006635769411L;
    private final TransferResponseModel model;

    public TxException(TransferResponseModel model) {
        super("");
        this.model = model;
    }

    public TransferResponseModel getModel() {
        return model;
    }

}
