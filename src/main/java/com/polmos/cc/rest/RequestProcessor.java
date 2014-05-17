package com.polmos.cc.rest;

import java.io.IOException;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface RequestProcessor {
    
    JsonObject processRequest(int range, String type) throws IOException;
}
