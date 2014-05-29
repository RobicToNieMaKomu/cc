package com.polmos.cc.db;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.polmos.cc.constants.Constants;
import com.polmos.cc.service.TimeUtils;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class DBUtilsImpl implements DBUtils {

    private static Logger logger = Logger.getLogger(DBUtilsImpl.class);
    
    @Inject
    private TimeUtils timeUtils;

    @Override
    public DBObject convertJson(JsonObject json) {
        DBObject output = null;
        logger.debug("Converting json:" + json);
        if (json != null) {
            output = (DBObject) JSON.parse(json.toString());
            String isoDate = "";
            if (json.containsKey(Constants.CREATION_TIME_PROPERTY)) {
                String aliorDate = json.getString(Constants.CREATION_TIME_PROPERTY);
                isoDate = timeUtils.toISO8601(aliorDate);
            } else {
                isoDate = timeUtils.toISO8601(new Date());
            }
            if (isoDate != null) {
                output.put(Constants.CREATION_TIME_PROPERTY, isoDate);
            }
        }
        logger.debug("Converted bson:" + output);
        return output;
    }

    @Override
    public JsonObject convertDBObject(DBObject dBObject) {
        String json = JSON.serialize(dBObject);
        return Json.createReader(new StringReader(json)).readObject();
    }

    @Override
    public JsonArray convertDBObject(List<DBObject> dBObjects) {
        JsonArray result = null;
        if (dBObjects != null) {
            JsonArrayBuilder builder = Json.createArrayBuilder();
            for (DBObject dbObject : dBObjects) {
                builder.add(convertDBObject(dbObject));
            }
            result = builder.build();
        }
        logger.info("Size of jsonArray:" + ((result != null) ? result.size() : null));
        return result;
    }
}
