package com.ripple.httpinterface;

import com.ripple.httpinterface.threads.ThreadsManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpInterfaceApplication {

    public static void main(String[] args) {
        ThreadsManager.startThreads();
        SpringApplication.run(HttpInterfaceApplication.class, args);
    }

}
