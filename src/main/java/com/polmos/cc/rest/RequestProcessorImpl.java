package com.polmos.cc.rest;

import com.mongodb.DBObject;
import com.polmos.cc.constants.OperationType;
import com.polmos.cc.db.DAO;
import com.polmos.cc.db.DBUtils;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.json.JsonArray;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class RequestProcessorImpl implements RequestProcessor {

    private static final Logger logger = Logger.getLogger(RequestProcessorImpl.class);

    @Inject
    private DAO dao;
    @Inject
    private DBUtils dbUtils;

    @Override
    public JsonArray processRequest(int range, String type) throws IOException {
        JsonArray result = null;
        validateInput(range, type);
        try {
            List<DBObject> docs = null;
            if (range == 0) {
                docs = dao.getRecentTwoDocuments();
            } else if (range > 0) {
                docs = dao.getDocuments(range);
            }
            logger.info("Docs to convert:" + ((docs != null) ? docs.size() : null));
            result = dbUtils.convertDBObject(docs);
        } catch (Exception e) {
            logger.error("Exception occurred during processing mst", e);
        }
        return result;
    }

    private OperationType validateInput(int range, String type) throws IOException {
        if (range < 0) {
            throw new IOException("Invalid input. Range should be equal to or greater than 0");
        }
        OperationType opType = OperationType.toOperationType(type);
        if (opType == null) {
            throw new IOException("Invalid input. Operation type should be bid or ask");
        }
        return opType;
    }
}