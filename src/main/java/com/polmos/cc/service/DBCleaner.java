package com.polmos.cc.service;

import com.polmos.cc.db.DAO;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class DBCleaner implements Runnable {

    private static final Logger logger = Logger.getLogger(DBCleaner.class);
    private static final int DAYS_BEFORE = 7;
    
    @Inject
    private DAO mongoDAO;
    
    @Override
    public void run() {
        logger.info("Removing documents older than " + DAYS_BEFORE + " days");
        int removed = mongoDAO.removeBefore(new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(DAYS_BEFORE)));
        logger.info("Successfully removed " + removed + " documents");
    }
}
