package com.polmos.cc.service.alior;

import java.io.IOException;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface AliorCsvParser {
    
    JsonObject parseRawText(String csv) throws IOException ;
}
