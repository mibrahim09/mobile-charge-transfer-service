package com.ripple.engine;

import com.ripple.engine.threads.ThreadsManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EngineApplication {

    public static void main(String[] args) {
        ThreadsManager.startThreads();
        SpringApplication.run(EngineApplication.class, args);
    }

}
