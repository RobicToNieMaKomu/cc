package com.polmos.cc.service.mst;

import java.util.List;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface FxParser {
    
    List<TimeWindow> toFxTimeSeries(List<JsonObject> rawTimeSeries);
}
