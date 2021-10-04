/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.models;

import com.ripple.httpinterface.enums.Enums;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ripple
 */
@Data
@NoArgsConstructor
public class TransferResponseModel {

    Long requestId;
    Long senderId;
    Long receiverId;
    double transferAmount;
    double senderPreviousAmount;
    double receiverPreviousAmount;
    double senderNewAmount;
    double receiverNewAmount;
    LocalDateTime transactionTimeStamp;
    int rejectionStatusCode;
    int statusCode;
    double fees, feesBefore, feesAfter;
    Enums.RejectionStatusCodes rejectionStatus;

    public TransferResponseModel(Enums.RejectionStatusCodes rejectionStatusCodes) {
        this.setRejection(rejectionStatusCodes);
    }

    public void setRejection(Enums.RejectionStatusCodes rejectionStatus) {
        this.rejectionStatusCode = rejectionStatus.ordinal();
        this.rejectionStatus = rejectionStatus;
    }
}
