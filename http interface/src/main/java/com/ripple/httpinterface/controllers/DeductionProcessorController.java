/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.httpinterface.controllers;

import com.ripple.httpinterface.constants.Constants;
import com.ripple.httpinterface.constants.Kernel;
import com.ripple.httpinterface.models.DeductionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ripple
 */
@RestController
@RequestMapping("/validate")
public class DeductionProcessorController {
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity deductFromCustomer(@RequestBody DeductionModel deductionModel) {
        try {
            Kernel.addToQueue(deductionModel);
            return new ResponseEntity(Constants.Defines.OK,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            // TODO: LOG EXCEPTION
            System.out.print(e);
            return new ResponseEntity(Constants.Defines.EXC,
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
