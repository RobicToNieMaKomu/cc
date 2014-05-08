package com.polmos.cc.service;

import java.util.Map;
import java.util.Set;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface JsonUtils {
    
    JsonObject convertMap(Map<String, Set<String>> map);

}
