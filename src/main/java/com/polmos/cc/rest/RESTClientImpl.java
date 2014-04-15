package com.polmos.cc.rest;

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;

/**
 *
 * @author RobicToNieMaKomu
 */
public class RESTClientImpl implements RESTClient {

    public RESTClientImpl() {
    }

    @Override
    public JsonObject sendGetRequest(String url) {
        JsonObject result = null;
        if (url != null && !url.isEmpty()) {
            try {
                Client client = ClientBuilder.newClient();
                Invocation request = client.target(url).request("application/csv").accept("application/csv").buildGet();
                String response = request.invoke(String.class);
                System.out.println(response);
                result = Json.createObjectBuilder().add("response", response).build();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return result;
    }
}
