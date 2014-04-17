package com.polmos.cc.rest;

import com.mongodb.DBObject;
import com.polmos.cc.db.DAO;
import com.polmos.cc.db.DBUtils;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    private RESTClient restClient;
    
    @Inject
    private DAO dao;
    
    @Inject
    private DBUtils dBUtils;
    
    @GET
    @Path("/hello")
    public JsonObject sayHello() {
        return Json.createObjectBuilder().add("Hello", "world").build();
    }
    
    @GET
    @Path("/data")
    public JsonObject getData() {
        return restClient.sendGetRequest("http://kantor.aliorbank.pl/forex/download/csv");
    }
    
    @GET
    @Path("/all")
    public JsonArray getAll() {
        JsonArray output = null;
        JsonArrayBuilder ab = Json.createArrayBuilder();
        List<DBObject> allDocuments = dao.getAllDocuments();
        if (allDocuments != null) {
            for (DBObject dbObject : allDocuments) {
                ab.add(dbObject.toString());
            }
        }
        logger.debug("Response for client:" + output);
        return output;
    }
    
    @POST
    @Path("/put")
    public void put(final JsonObject body) {
        dao.createDocument(dBUtils.convertJson(body));
    }
}
