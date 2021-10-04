package com.ripple.engine;

import com.ripple.engine.constants.Kernel;
import com.ripple.engine.db.DatabaseManager;
import com.ripple.engine.services.ConstantsService;
import com.ripple.engine.threads.ThreadsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EngineApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EngineApplication.class, args);

    }

    @Autowired
    ConstantsService constantsSVR;

    @Autowired
    ThreadsManager threadsMgr;

    @Autowired
    DatabaseManager dbMgr;

    public void run(String... args) throws Exception {
        Kernel.constantsService = constantsSVR;
        Kernel.dbManager = dbMgr;
        constantsSVR.loadDbConstants();
        threadsMgr.startThreads();
    }
}
