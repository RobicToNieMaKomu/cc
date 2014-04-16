package com.polmos.cc.db;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.polmos.cc.constants.Constants;
import com.polmos.cc.service.TimeUtils;
import java.util.Date;
import javax.inject.Inject;
import javax.json.JsonObject;
import org.bson.types.ObjectId;

/**
 *
 * @author RobicToNieMaKomu
 */
public class DBUtilsImpl implements DBUtils {

    private static final String MONGO_ID_KEY = "_id";
    
    @Inject
    private TimeUtils timeUtils;

    @Override
    public DBObject convertJson(JsonObject json) {
        DBObject output = null;
        if (json != null) {
            output = (DBObject) JSON.parse(json.toString());
            String isoDate = "";
            if (json.containsKey(Constants.EX_RATE_ID)) {
                String aliorDate = json.getString(Constants.EX_RATE_ID);
                isoDate = timeUtils.toISO8601(aliorDate);
            } else {
                isoDate = timeUtils.toISO8601(new Date());
            }
            ObjectId id = new ObjectId(isoDate);
            output.put(MONGO_ID_KEY, id);
        }
        return output;
    }
}
