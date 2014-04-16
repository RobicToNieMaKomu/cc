package com.polmos.cc.db;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author RobicToNieMaKomu
 */
public class DAOImpl implements DAO {

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
        DB db = mongoDBConnector.getDB();
        try {
            db.requestStart();
            DBCollection rates = db.getCollection(EXCHANGE_RATES_COLLECTION);
            DBCursor iterator = rates.find();
            while (iterator.hasNext()) {
                output.add(iterator.next());
            }
        } finally {
            db.requestDone();
        }
        return output;
    }
}