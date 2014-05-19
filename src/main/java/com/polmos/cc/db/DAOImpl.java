package com.polmos.cc.db;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.polmos.cc.service.TimeUtils;
import com.polmos.cc.service.yahoo.annotation.Now;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class DAOImpl implements DAO {

    private static final Logger logger = Logger.getLogger(DAOImpl.class);
    private static final int MAX_DAILY_DOC_COUNT = 480;
    private static final String EXCHANGE_RATES_COLLECTION = "rates";
    @Inject
    private MongoDBConnector mongoDBConnector;
    @Inject
    private TimeUtils timeUtils;
    @Inject
    @Now
    private Instance<Date> now;

    public DAOImpl() {
    }

    @Override
    public boolean createDocument(DBObject data) {
        boolean success = false;
        if (data != null) {
            DB db = mongoDBConnector.getDB();
            try {
                db.requestStart();
                DBCollection rates = db.getCollection(EXCHANGE_RATES_COLLECTION);
                rates.insert(data);
            } finally {
                db.requestDone();
            }
        }
        return success;
    }

    @Override
    public List<DBObject> getAllDocuments() {
        List<DBObject> output = new ArrayList<>();
        logger.debug("Getting all documents from db");
        DB db = mongoDBConnector.getDB();
        try {
            db.requestStart();
            DBCollection rates = db.getCollection(EXCHANGE_RATES_COLLECTION);
            logger.debug("DB returned collection:" + rates);
            DBCursor iterator = rates.find();
            while (iterator.hasNext()) {
                DBObject nextItem = iterator.next();
                logger.debug("Next dbObject:" + nextItem);
                output.add(nextItem);
            }
        } finally {
            db.requestDone();
        }
        return output;
    }

    @Override
    public List<DBObject> getDocuments(int days) {
        List<DBObject> documents = new ArrayList<>();
        if (days == 0) {
            documents = getRecentTwoDocuments();
        } else if (days > 0) {
            for (int i = 0; i < days; i++) {
                Date from = new Date(now.get().getTime() - (i+1)*TimeUnit.DAYS.toMillis(1));
                Date to = new Date(from.getTime() + TimeUnit.DAYS.toMillis(1));
                documents.addAll(documentsFromTo(from, to));
            }
        }
        logger.info("Num of requested documents:" + documents.size());
        return documents;
    }

    @Override
    public List<DBObject> getRecentTwoDocuments() {
        List<DBObject> documents = new ArrayList<>();
        DB db = mongoDBConnector.getDB();
        db.requestStart();
        try {
            DBCursor cursor = db.getCollection(EXCHANGE_RATES_COLLECTION).find().sort(new BasicDBObject("_id", -1)).limit(2);
            documents.addAll(cursor.toArray());
        } finally {
            db.requestDone();
        }
        logger.info("Num of requested documents:" + documents.size());
        return documents;
    }

    private List<DBObject> documentsFromTo(Date from, Date to) {
        List<DBObject> output = new ArrayList<>();
        DB db = mongoDBConnector.getDB();
        db.requestStart();
        try {
            String fromDate = timeUtils.toISO8601(from);
            String toDate = timeUtils.toISO8601(to);
            DBObject query = BasicDBObjectBuilder.start("$gte", fromDate).add("$lte", toDate).get();
            DBCursor cursor = db.getCollection(EXCHANGE_RATES_COLLECTION).find(query).sort(new BasicDBObject("_id", -1)).limit(MAX_DAILY_DOC_COUNT);

            output.addAll(cursor.toArray());
        } finally {
            db.requestDone();
        }
        return output;
    }
}