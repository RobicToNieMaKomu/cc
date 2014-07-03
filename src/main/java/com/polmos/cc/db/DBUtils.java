package com.polmos.cc.db;

import com.mongodb.DBObject;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface DBUtils {
    
    DBObject convertJson(JsonObject json);
    JsonObject convertDBObject(DBObject dBObject);
    JsonArray convertDBObject(List<DBObject> dBObjects, List<String> currencies);
}
