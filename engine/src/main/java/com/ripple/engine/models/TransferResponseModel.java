/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.models;

import com.ripple.engine.enums.Enums;
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
    Enums.RejectionStatusCodes rejectionStatusCode;
    Enums.StatusCodes statusCode;

}
