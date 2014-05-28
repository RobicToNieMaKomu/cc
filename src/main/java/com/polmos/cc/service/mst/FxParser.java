package com.polmos.cc.service.mst;

import java.util.List;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface FxParser {
    
    TimeWindow toFxTimeSeries(JsonObject singleRawTimeSeries);
    
    List<TimeWindow> toFxTimeSeries(List<JsonObject> rawTimeSeries);
}
