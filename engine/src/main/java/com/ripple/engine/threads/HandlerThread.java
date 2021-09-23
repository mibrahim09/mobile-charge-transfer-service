/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ripple.engine.threads;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ripple.engine.constants.Constants;
import com.ripple.engine.constants.Kernel;
import com.ripple.engine.models.DeductionModel;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ripple
 */
public class HandlerThread extends TimerThread {

    public HandlerThread() {
        super(Constants.Defines.HandlerThreadCooldown);
    }

    @Override
    public void threadAction() {
        try {
            DeductionModel model = Kernel.dequeue();
            HttpClient client = null;

            while (model != null) {
                if (client == null) {
                    client = HttpClient.newHttpClient();
                }

                Map values = new HashMap<String, String>();
                values.put("senderId", model.getSenderId());
                values.put("receiverId", model.getReceiverId());
                values.put("amount", model.getAmount());
                values.put("requestId", model.getRequestId());

                ObjectMapper objectMapper = new ObjectMapper();
                String requestBody = objectMapper
                        .writeValueAsString(values);

                HttpRequest request = HttpRequest.newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .header("Content-Type", "application/json")
                        .uri(URI.create(Constants.Statics.HttpServicelink))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    System.out.println("Response:" + response.statusCode() + ",msg:" + response.body());
                }
                
                model = Kernel.dequeue();
            }
        } catch (Exception e) {
        }

    }
}
