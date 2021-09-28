package com.ripple.httpinterface;

import com.ripple.httpinterface.constants.Kernel;
import com.ripple.httpinterface.db.DatabaseManager;
import com.ripple.httpinterface.services.ConstantsService;
import com.ripple.httpinterface.threads.ThreadsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
//        ConstantsService service = Kernel.getConstantsService();

//        ctx.getAutowireCapableBeanFactory().autowireBeanProperties(
//                service,
//                AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT, true);
        Kernel.constantsService = constantsSVR;
        Kernel.dbManager = dbMgr;

        constantsSVR.loadConstants();
        threadsMgr.startThreads();
    }
}
