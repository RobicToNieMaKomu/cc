package com.polmos.cc.db;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class DAOImpl implements DAO {

    private static final Logger logger = Logger.getLogger(DAOImpl.class);
    private static final String EXCHANGE_RATES_COLLECTION = "rates";
   
    @Inject
    private MongoDBConnector mongoDBConnector;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DBObject> getRecentTwoDocuments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}