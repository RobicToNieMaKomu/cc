package com.polmos.cc.service.yahoo;

import com.polmos.cc.service.mst.ExRate;
import com.polmos.cc.service.mst.FxParser;
import com.polmos.cc.service.mst.TimeWindow;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class YahooFxParser implements FxParser {

    private static final Logger logger = Logger.getLogger(YahooFxParser.class);
    private static final String CURR_ID_PROPERTY = "id";
    private static final String BID_PROPERTY = "Bid";
    private static final String ASK_PROPERTY = "Ask";
    private static final String QUERY_PROPERTY = "query";
    private static final String RESULT_PROPERTY = "results";
    private static final String RATE_PROPERTY = "rate";

    @Override
    public TimeWindow toFxTimeSeries(JsonObject rawTimeWindow) {
        TimeWindow output = null;
        if (rawTimeWindow != null) {
            List<ExRate> exRates = new ArrayList<>();
            JsonObject queryJson = toQueryJson(rawTimeWindow);
            JsonObject resultJson = toResultJson(queryJson);
            JsonArray jsonExRates = toJsonExRates(resultJson);
            if (jsonExRates != null) {
                for (int i = 0; i < jsonExRates.size(); i++) {
                    ExRate exRate = toExRate(jsonExRates.getJsonObject(i));
                    if (exRate != null) {
                        exRates.add(exRate);
                    }
                }
            }
            output = new TimeWindow(exRates);
        }
        return output;
    }

    @Override
    public List<TimeWindow> toFxTimeSeries(List<JsonObject> rawTimeWindows) {
        List<TimeWindow> output = new ArrayList<>();
        if (rawTimeWindows != null) {
            for (JsonObject json : rawTimeWindows) {
                output.add(toFxTimeSeries(json));
            }
        }
        return output;
    }

    private JsonArray toJsonExRates(JsonObject json) {
        JsonArray output = null;
        if (json != null && json.containsKey(RATE_PROPERTY)) {
            output = json.getJsonArray(RATE_PROPERTY);
        }
        return output;
    }

    private JsonObject toQueryJson(JsonObject json) {
        JsonObject output = null;
        if (json != null && json.containsKey(QUERY_PROPERTY)) {
            output = json.getJsonObject(QUERY_PROPERTY);
        }
        return output;
    }

    private JsonObject toResultJson(JsonObject json) {
        JsonObject output = null;
        if (json != null && json.containsKey(RESULT_PROPERTY)) {
            output = json.getJsonObject(RESULT_PROPERTY);
        }
        return output;
    }

    private ExRate toExRate(JsonObject json) {
        ExRate output = null;
        if (json != null && json.containsKey(CURR_ID_PROPERTY) && json.containsKey(BID_PROPERTY) && json.containsKey(ASK_PROPERTY)) {
            String id = json.getString(CURR_ID_PROPERTY);
            String bid = json.getString(BID_PROPERTY);
            String ask = json.getString(ASK_PROPERTY);
            output = new ExRate(id, toFloat(ask), toFloat(bid));
        } else {
            logger.warn("Suspicious JSON:" + json);
        }
        return output;
    }

    private float toFloat(String value) {
        float output = 0;
        try {
            output = (value != null && !"N/A".equals(value)) ? Float.parseFloat(value) : 0;
        } catch (NumberFormatException nfe) {
            logger.error("Couldnt parse bid or ask:" + value, nfe);
        }
        return output;
    }
}
