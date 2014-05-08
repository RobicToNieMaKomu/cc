package com.polmos.cc.service.mst;

import com.mongodb.DBObject;
import com.polmos.cc.constants.BundleName;
import com.polmos.cc.db.DAO;
import com.polmos.cc.db.DBUtils;
import com.polmos.cc.service.ResourceManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.json.JsonObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public class MSTServiceImpl implements MSTService {

    @Inject
    private MSTUtils mstUtils;
    @Inject
    private DBUtils dbUtils;
    @Inject
    private DAO dao;
    @Inject
    private FxParser parser;

    @Override
    public Map<String, Set<String>> generateMST(int days) throws IOException{
        List<DBObject> docs = null;
        if (days == 0) {
            docs = dao.getRecentTwoDocuments();
        } else if (days > 0) {
            docs = dao.getDocuments(days);
        }
        List<JsonObject> documents = dbUtils.convertDBObject(docs);
        float[][] correlationMx = parser.parseFxTimeSeries(documents);
        float[][] distanceMx = mstUtils.convertCorrelationMxToDistanceMx(correlationMx);
        List<String> currencies = ResourceManager.getAllKeys(BundleName.CURRENCIES);
        return mstUtils.constructMst(currencies, distanceMx);
    }
}
