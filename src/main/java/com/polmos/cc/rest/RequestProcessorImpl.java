package com.polmos.cc.rest;

import com.polmos.cc.constants.OperationType;
import com.polmos.cc.service.JsonUtils;
import com.polmos.cc.service.mst.MSTService;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.json.JsonObject;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class RequestProcessorImpl implements RequestProcessor {

    private static final Logger logger = Logger.getLogger(RequestProcessorImpl.class);
    @Inject
    private MSTService mstService;
    @Inject
    private JsonUtils jsonUtils;

    @Override
    public JsonObject processRequest(int range, String type) throws IOException {
        JsonObject result = null;
        OperationType operationType = validateInput(range, type);
        try {
            Map<String, Set<String>> mst = mstService.generateMST(range, operationType);
            result = jsonUtils.convertMap(mst);
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
