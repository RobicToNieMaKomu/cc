package com.polmos.cc.rest;

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
    public JsonObject processRequest(int range) throws IOException {
        JsonObject result = null;
        if (range < 0) {
            throw new IOException("Invalid input. Range should be equal to or greater than 0");
        } else {
            try {
                Map<String, Set<String>> mst = mstService.generateMST(range);
                result = jsonUtils.convertMap(mst);
            } catch (Exception e) {
                logger.error("Exception occurred during processing mst" ,e);
            }
        }
        return result;
    }
}
