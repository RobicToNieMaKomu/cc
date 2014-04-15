package com.polmos.cc.rest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author RobicToNieMaKomu
 */
@Path("/")
@Produces("application/json")
@Consumes("application/json")
public class RESTResources {

    @GET
    @Path("/hello")
    public JsonObject sayHello() {
        return Json.createObjectBuilder().add("Hello", "world").build();
    }
}
