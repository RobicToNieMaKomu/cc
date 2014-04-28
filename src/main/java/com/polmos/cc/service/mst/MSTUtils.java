package com.polmos.cc.service.mst;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MSTUtils {

    float[][] convertCorrelationMxToDistanceMx(float [][] correlationMx) throws IOException;
    
    Map<Node, Set<Node>> constructMst(float[][] distanceMx) throws IOException;
    
    /**
     * Returns list of node pairs, sorted by their distances asc.
     * Eg. {[PLN,USD], [AUD,EUR]...}
     * @param nodes
     * @return 
     */
    List<Node[]> sortByDistanceAsc(List<Node> nodes, float[][] distanceMx) throws IOException;
}
