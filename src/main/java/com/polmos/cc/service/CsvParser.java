package com.polmos.cc.service;

import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface CsvParser {
    
    JsonObject parseRawText(String csv);
}
