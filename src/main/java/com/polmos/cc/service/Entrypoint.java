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
    
    private final long fxPollInterval = 1;
    private final long cleanInterval = 1;
    private ScheduledFuture<?> fxDownloaderFuture;
    private ScheduledFuture<?> dbCleanerFuture;
    
    @Resource
    private ManagedScheduledExecutorService executorService;
    
    @Inject
    private YahooFxDownloader task;
    
    @Inject
    private DBCleaner dbCleaner;
    
    @PostConstruct
    public void initialize() { 
        logger.info("Application is starting...");
        fxDownloaderFuture = executorService.scheduleAtFixedRate(task, 0, fxPollInterval, TimeUnit.MINUTES);
        dbCleanerFuture = executorService.scheduleAtFixedRate(dbCleaner, 0, cleanInterval, TimeUnit.DAYS);
    }
    
    @PreDestroy
    public void clean() {
        logger.info("Interrupting scheduled tasks");
        fxDownloaderFuture.cancel(true);
        dbCleanerFuture.cancel(true);
    }
}
