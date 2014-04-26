package com.polmos.cc.service.yahoo;

import com.mongodb.DBObject;
import com.polmos.cc.constants.BundleName;
import com.polmos.cc.db.DAO;
import com.polmos.cc.db.DBUtils;
import com.polmos.cc.rest.RESTClient;
import com.polmos.cc.service.ResourceManager;
import com.polmos.cc.service.yahoo.annotation.Now;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.json.JsonObject;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class YahooFxDownloader implements Runnable {

    private static final Logger logger = Logger.getLogger(YahooFxDownloader.class);
    private static final String URL_TO_YAHOO_FINANCE = "https://query.yahooapis.com/v1/public/yql?q=";
    private static final String JSON_FORMAT = "&format=json&env=store://datatables.org/alltableswithkeys&callback=";
    // assuming fx is in ED timezone and opened at 8am and closed at 4pm except weekends
    private static final int OH = 8;
    private static final int CH = 16;
    private static final int SAT = 7;
    private static final int SUN = 1;
    @Inject
    private RESTClient restClient;
    @Inject
    private YQLQueryBuilder yqLQueryBuilder;
    @Inject
    private DBUtils dBUtils;
    @Inject
    private DAO dao;
    @Inject
    @Now
    private Instance<Date> now;

    @Override
    public void run() {
        logger.info("Polling new data from Yahoo finance...");
        try {
            Date time = now.get();
            if (isYahooOpen(time)) {
                String query = yqLQueryBuilder.constructSelectQuery(ResourceManager.getAllKeys(BundleName.CURRENCIES));
                logger.info("query:" + query);
                JsonObject response = restClient.sendGetRequest(URL_TO_YAHOO_FINANCE + query + JSON_FORMAT);
                logger.info("response:" + response);
                DBObject dbObject = dBUtils.convertJson(response);
                logger.info("dbObject:" + dbObject);
                dao.createDocument(dbObject);
                logger.info("Poll completed");
            } else {
                logger.info("Yahoo finance service is closed, skipping (" + time + ")");
            }
        } catch (Exception e) {
            logger.error("Exception in YahooFxDownloader", e);
        }
    }

    private boolean isYahooOpen(Date date) {
        boolean opened = false;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (dayOfWeek != SAT && dayOfWeek != SUN && hour >= OH && hour <= CH && !(hour == CH && minute != 0)) {
            opened = true;
        }
        return opened;
    }
}