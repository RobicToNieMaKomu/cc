package com.polmos.cc.db;

import com.mongodb.DBObject;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface DAO {
    boolean createDocument(DBObject data);
    List<DBObject> getAllDocuments();
    List<DBObject> getRecentTwoDocuments();
    List<DBObject> getDocuments(Time time);
    
    enum Time {
        TEN_MINUTES, 
        ONE_HOUR,
        ONE_DAY,
        ONE_WEEK,
        ONE_MONTH,
        ALL
    }
}
