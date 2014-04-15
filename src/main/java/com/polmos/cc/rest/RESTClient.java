package com.polmos.cc.rest;

import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface RESTClient {

    JsonObject sendGetRequest(String url);
}
