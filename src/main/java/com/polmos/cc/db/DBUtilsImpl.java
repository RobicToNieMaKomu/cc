package com.polmos.cc.db;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.polmos.cc.constants.Constants;
import com.polmos.cc.service.TimeUtils;
import java.util.Date;
import javax.inject.Inject;
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
}
