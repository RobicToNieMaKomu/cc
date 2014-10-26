package com.polmos.cc.db;

import com.mongodb.DBObject;
import com.polmos.cc.db.commands.Queryable;

import java.util.Date;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface DAO {
    boolean createDocument(DBObject data);
    List<DBObject> getDocuments(Queryable query);
    int removeBefore(Date date);
}
