package com.polmos.cc.db;

import com.mongodb.DBObject;
import java.util.List;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface DBUtils {
    
    DBObject convertJson(JsonObject json);
    JsonObject convertDBObject(DBObject dBObject);
    List<JsonObject> convertDBObject(List<DBObject> dBObjects);
}
