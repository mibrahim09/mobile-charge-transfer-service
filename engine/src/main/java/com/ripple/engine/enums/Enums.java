/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.enums;

/**
 *
 * @author Ripple
 */
public class Enums {

    public enum RejectionStatusCodes {
        None,
        ServerDisabled,
        InvalidChannelId,
        Exception
    }

    public enum StatusCodes {
        Success,
        Failed,
        Rejected
    }

}
