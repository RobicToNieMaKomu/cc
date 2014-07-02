package com.polmos.cc.rest;

import java.io.IOException;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
@Path("/")
@Produces("application/json")
@Consumes("application/json")
public class RESTResources {

    private static final Logger logger = Logger.getLogger(RESTResources.class);

    @Inject
    private RequestProcessor processor;

    @GET
    @Path("/mst")
    public Response getTimeSeries(@QueryParam("range") int minutes, @QueryParam("type") String operationType, @QueryParam("currencies") String currencies) {
        Response response = null;
        try {
            JsonArray timeSeries = processor.processRequest(minutes, operationType, currencies);
            if (timeSeries == null) {
                response = Response.status(Status.NOT_FOUND).build();
            } else {
                response = Response.ok(timeSeries).build();
            }
        } catch (IOException ex) {
            logger.error("Exception while processing REST call", ex);
            response = Response.status(Status.BAD_REQUEST).build();
        }
        return response;
    }
}
