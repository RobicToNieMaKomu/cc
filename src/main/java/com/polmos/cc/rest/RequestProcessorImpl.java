package com.polmos.cc.rest;

import com.mongodb.DBObject;
import com.polmos.cc.constants.BundleName;
import com.polmos.cc.constants.OperationType;
import com.polmos.cc.db.DAO;
import com.polmos.cc.db.DBUtils;
import com.polmos.cc.service.ResourceManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final String SEPARATOR = ",";
    
    @Inject
    private DAO dao;
    @Inject
    private DBUtils dbUtils;

    @Override
    public JsonArray processRequest(int range, String type, String currencies) throws IOException {
        JsonArray result = null;
        List<String> listOfCurrencies = toList(currencies);
        validateInput(range, type, listOfCurrencies);
        try {
            List<DBObject> docs = null;
            if (range == 0) {
                docs = dao.getRecentTwoDocuments(listOfCurrencies);
            } else if (range > 0) {
                docs = dao.getDocuments(range, listOfCurrencies);
            }
            logger.info("Docs to convert:" + ((docs != null) ? docs.size() : null));
            result = dbUtils.convertDBObject(docs, listOfCurrencies);
        } catch (Exception e) {
            logger.error("Exception occurred during processing mst", e);
        }
        return result;
    }

    private OperationType validateInput(int range, String type, List<String> currencies) throws IOException {
        if (currencies == null) {
            throw new IOException("Invalid input. At least 2 currencies expected");
        }
        List<String> allCurrencies = ResourceManager.getAllKeys(BundleName.CURRENCIES);
        for (String curr : currencies) {
            if (!allCurrencies.contains(curr)) {
                throw new IOException("Invalid input. Unsupported symbol of currency:" + curr);
            }
        }
        if (range < 0) {
            throw new IOException("Invalid input. Range should be equal to or greater than 0");
        }
        OperationType opType = OperationType.toOperationType(type);
        if (opType == null) {
            throw new IOException("Invalid input. Operation type should be bid or ask");
        }
        return opType;
    }
    
    private List<String> toList(String currencies) {
        List<String> output = null;
        if (currencies != null) {
            String[] splited = currencies.split(SEPARATOR);
            if (splited.length > 1) {
                output = new ArrayList<>(Arrays.asList(splited));
            }
        }
        return output;
    }
}