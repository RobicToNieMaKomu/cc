package com.polmos.cc.service;

import com.polmos.cc.service.yahoo.YahooFxDownloader;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
@Singleton
@Startup
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class Entrypoint {

    private static final Logger logger = Logger.getLogger(Entrypoint.class);
    
    private long fxPollInterval = 1;
    private ScheduledFuture<?> scheduledFuture;
    
    @Resource
    private ManagedScheduledExecutorService executorService;
    
    @Inject
    private YahooFxDownloader task;
    
    @PostConstruct
    public void initialize() { 
        logger.info("Application is starting...");
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        scheduledFuture = executorService.scheduleAtFixedRate(task, 0, fxPollInterval, TimeUnit.MINUTES);
    }
    
    @PreDestroy
    public void clean() {
        logger.info("Interrupting scheduled tasks");
        scheduledFuture.cancel(true);
    }
}
