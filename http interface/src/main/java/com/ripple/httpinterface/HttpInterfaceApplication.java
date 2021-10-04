package com.ripple.httpinterface;

import com.ripple.httpinterface.constants.Kernel;
import com.ripple.httpinterface.db.DatabaseManager;
import com.ripple.httpinterface.enums.Enums;
import com.ripple.httpinterface.services.ConstantsService;
import com.ripple.httpinterface.threads.ThreadsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class HttpInterfaceApplication implements CommandLineRunner {

    @Autowired
    ConstantsService constantsSVR;

    @Autowired
    ThreadsManager threadsMgr;

    @Autowired
    DatabaseManager dbMgr;

    public static void main(String[] args) {
        SpringApplication.run(HttpInterfaceApplication.class, args);

    }

    public void run(String... args) throws Exception {
        Kernel.constantsService = constantsSVR;
        Kernel.dbManager = dbMgr;

        Enums.insertRejectionStatusCodes();

        constantsSVR.loadConstants();
        threadsMgr.startThreads();
    }
}
