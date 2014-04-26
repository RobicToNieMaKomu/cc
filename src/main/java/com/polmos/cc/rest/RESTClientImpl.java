package com.polmos.cc.rest;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class RESTClientImpl implements RESTClient {

    private static final Logger logger = Logger.getLogger(RESTClientImpl.class);
    
    public RESTClientImpl() {
    }

    @Override
    public JsonObject sendGetRequest(String url) {
        JsonObject result = null;
        if (url != null && !url.isEmpty()) {
            logger.debug("Sending get request to url:" + url);
            try {
                Client client = ClientBuilder.newClient();
                Invocation request = client.target(url).request("application/json").accept("application/json").buildGet();
                String response = request.invoke(String.class);
                logger.debug("Response:" + response);
                result = Json.createReader(new StringReader(response)).readObject();
            } catch (Exception e) {
                logger.error("Couldnt get resource", e);
            }
        }
        return result;
    }
}
