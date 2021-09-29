/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.models;

import com.ripple.engine.enums.Enums.RejectionStatusCodes;
import com.ripple.engine.enums.Enums.StatusCodes;

/**
 *
 * @author Ripple
 */
public class DeductionResponseModel {

    int requestId;
    StatusCodes responseCode;
    RejectionStatusCodes rejectionCode;
}
