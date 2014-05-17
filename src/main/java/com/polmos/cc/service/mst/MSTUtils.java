package com.polmos.cc.service.mst;

import com.polmos.cc.constants.OperationType;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MSTUtils {

    float[][] generateCorrelationMx(List<String> currencySymbols, List<TimeWindow> timeSeries, OperationType type);
    
    float[][] convertCorrelationMxToDistanceMx(float [][] correlationMx) throws IOException;
    
    Map<String, Set<String>> constructMst(List<String> currencySymbols, float[][] distanceMx) throws IOException;
    
    /**
     * Returns list of currency pairs, separated by '$$' and sorted by their distances asc.
     * Eg. ["PLN$$USD", "AUD$$EUR" ... ]
     * 
     * @param currencySymbols
     * @param distanceMx
     * @return
     * @throws IOException 
     */
    List<String> sortByDistanceAsc(List<String> currencySymbols, float[][] distanceMx) throws IOException;
}
