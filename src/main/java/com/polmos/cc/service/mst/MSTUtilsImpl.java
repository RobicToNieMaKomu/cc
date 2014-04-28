package com.polmos.cc.service.mst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public class MSTUtilsImpl implements MSTUtils {

    @Override
    public float[][] convertCorrelationMxToDistanceMx(float[][] correlationMx) throws IOException {
        validateInputMx(correlationMx);
        int degree = correlationMx.length;
        float[][] output = new float[degree][degree];
        for (int i = 0; i < degree; i++) {
            for (int j = 0; j < degree; j++) {
                output[i][j] = (float) Math.sqrt(0.5*(1-correlationMx[i][j]));
            }
        }
        return output;
    }

    @Override
    public Map<Node, Set<Node>> constructMst(float[][] distanceMx) throws IOException {
        validateInputMx(distanceMx);
        Map<Node, Set<Node>> output = new HashMap<>();
        // Kruskal algorithm
        
        return output;
    }

    @Override
    public List<Node[]> sortByDistanceAsc(List<Node> nodes, float[][] distanceMx) throws IOException {
        validateInputMx(distanceMx);
        List<Node[]> output = new ArrayList<>();
        if (nodes != null && nodes.size() == distanceMx.length) {
            
        }
        return output;
    }

    private void validateInputMx(float[][] correlationMx) throws IOException {
        if (correlationMx == null || correlationMx.length < 2) {
            throw new IOException("Invalid size of matrix");
        } else {
            int expectedColSize = correlationMx.length;
            for (int i = 0; i < expectedColSize; i++) {
                if (correlationMx[i] != null && (correlationMx[i].length != expectedColSize)) {
                    throw new IOException("Not a square matrix");
                }
            }
        }
    }
    
    
}