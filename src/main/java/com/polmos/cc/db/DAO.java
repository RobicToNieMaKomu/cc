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
    List<DBObject> getDocuments(int days);
}
