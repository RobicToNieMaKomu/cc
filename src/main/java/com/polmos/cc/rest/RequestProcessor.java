package com.polmos.cc.rest;

import java.io.IOException;
import javax.json.JsonArray;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface RequestProcessor {
    
    JsonArray processRequest(int range, String type, String currencies) throws IOException;
}
