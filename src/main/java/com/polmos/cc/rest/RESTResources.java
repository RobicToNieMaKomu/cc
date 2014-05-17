package com.polmos.cc.rest;

import java.io.IOException;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
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
    public JsonObject getMST(@QueryParam("range") int minutes, @QueryParam("type") String operationType) {
        try {
            return processor.processRequest(minutes, operationType);
        } catch (IOException ex) {
            logger.error("Exception while processing REST call", ex);
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
    }
}