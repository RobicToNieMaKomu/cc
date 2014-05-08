package com.polmos.cc.service.mst;

import java.util.List;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface FxParser {

    float[][] parseFxTimeSeries(List<JsonObject> timeSeries);
}
