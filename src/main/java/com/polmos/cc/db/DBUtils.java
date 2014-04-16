package com.polmos.cc.db;

import com.mongodb.DBObject;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface DBUtils {
    
    DBObject convertJson(JsonObject json);
    
}
