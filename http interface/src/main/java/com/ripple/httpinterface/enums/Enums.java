/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.enums;

import com.ripple.httpinterface.constants.Kernel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Ripple
 */
public class Enums {

    public static Logger logger = LogManager.getLogger(Enums.class);

    public static void insertRejectionStatusCodes() {
        try {
            JdbcTemplate template = Kernel.dbManager.getConnection();

            // Clear the old error codes
            template.update("DELETE from error_codes");

            // New error codes.
            for (RejectionStatusCodes enu : RejectionStatusCodes.values()) {
                template.update("insert into error_codes values (?,?)",
                        new Object[]{
                            enu.ordinal(),
                            enu.name()});
                logger.debug("code:" + enu.name() + ",val:" + enu.ordinal());
            }
        } catch (DataAccessException e) {
            logger.error(e);
        }
    }

    public enum RejectionStatusCodes {
        OK,
        Exception,
        ServerDisabled,
        InvalidChannelId,
        InvalidMsdns,
        NotEnoughBalance,
        SameMsdn
    }

    public enum StatusCodes {
        Success,
        Failed,
        Rejected
    }

}
