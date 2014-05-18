package com.polmos.cc.service.mst;

import com.polmos.cc.constants.OperationType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class MSTUtilsImpl implements MSTUtils {

    private static final Logger logger = Logger.getLogger(MSTUtilsImpl.class);
    private static final String SEPARATOR = "$$";
    private static final String SEPARATOR_REGEX = "\\$\\$";

    @Override
    public float[][] convertCorrelationMxToDistanceMx(float[][] correlationMx) throws IOException {
        validateInputMx(correlationMx);
        int degree = correlationMx.length;
        float[][] output = new float[degree][degree];
        for (int i = 0; i < degree; i++) {
            for (int j = 0; j < degree; j++) {
                output[i][j] = (float) Math.sqrt(0.5 * (1 - correlationMx[i][j]));
            }
        }
        return output;
    }

    @Override
    public Map<String, Set<String>> constructMst(List<String> currencySymbols, float[][] distanceMx) throws IOException {
        validateInputMx(distanceMx);
        Map<String, Set<String>> graph = initGraph(currencySymbols);
        Set<Set<String>> forest = initForest(currencySymbols);
        List<String> sortedEdges = sortByDistanceAsc(currencySymbols, distanceMx);
        for (String edge : sortedEdges) {
            if (forest.size() != 1) {
                String[] nodes = edge.split(SEPARATOR_REGEX);
                String currA = nodes[0];
                String currB = nodes[1];

                Set<String> nA = findForestContainingTree(forest, currA);
                Set<String> nB = findForestContainingTree(forest, currB);
                nA.addAll(nB);
                forest.add(nA);

                Set<String> neighborsA = graph.get(currA);
                Set<String> neighborsB = graph.get(currB);
                neighborsA.add(currB);
                neighborsB.add(currA);
                graph.put(currA, neighborsA);
                graph.put(currB, neighborsB);
            } else {
                break;
            }
        }
        return graph;
    }

    @Override
    public List<String> sortByDistanceAsc(List<String> currencySymbols, float[][] distanceMx) throws IOException {
        validateInputMx(distanceMx);
        List<String> output = new ArrayList<>();
        int degree = distanceMx.length;
        if (currencySymbols != null && currencySymbols.size() == degree) {
            Map<String, Float> currToDstMap = new HashMap<>();
            for (int i = 0; i < degree; i++) {
                for (int j = i; j < degree; j++) {
                    if (i != j) {
                        currToDstMap.put(toStr(i, j, currencySymbols), distanceMx[i][j]);
                    }
                }
            }
            List<Map.Entry<String, Float>> listOfEntries = new ArrayList<>(currToDstMap.entrySet());
            Collections.sort(listOfEntries, createMapComparator());
            for (Map.Entry<String, Float> entry : listOfEntries) {
                output.add(entry.getKey());
            }
        }
        return output;
    }

    @Override
    public float[][] generateCorrelationMx(List<String> currencySymbols, List<TimeWindow> timeSeries, OperationType type) {
        float[][] output = null;
        if (timeSeries != null && currencySymbols != null && type != null) {
            int dimm = currencySymbols.size();
            output = new float[dimm][dimm];
            for (int i = 0; i < dimm; i++) {
                String currA = currencySymbols.get(i);
                float avgA = averageValue(currA, timeSeries, type);
                for (int j = i; j < dimm; j++) {
                    if (i != j) {
                        String currB = currencySymbols.get(j);
                        float avgB = averageValue(currB, timeSeries, type);
                        float numerator = 0;
                        float sa = 0;
                        float sb = 0;
                        for (TimeWindow timeWindow : timeSeries) {
                            ExRate exRateA = timeWindow.forCurrency(currA);
                            ExRate exRateB = timeWindow.forCurrency(currB);
                            if (exRateA != null && exRateB != null) {
                                float rA = exRateA.getValue(type);
                                float rB = exRateB.getValue(type);
                                numerator += (rA - avgA)*(rB - avgB);
                                sa += (rA-avgA)*(rA-avgA); 
                                sb += (rB-avgB)*(rB-avgB);
                            }
                        }
                        float denominator = (sa != 0 && sb != 0) ? (float) (Math.sqrt(sa) * Math.sqrt(sb)) : 1;
                        output[i][j] = numerator / denominator;
                        output[j][i] = output[i][j];
                    } else {
                        output[i][j] = 1;
                    }
                }
            }
        }
        return output;
    }

    private float averageValue(String currency, List<TimeWindow> timeSeries, OperationType type) {
        float avg = 0;
        for (TimeWindow timeWindow : timeSeries) {
            ExRate exRate = timeWindow.forCurrency(currency);
            avg += exRate.getValue(type);
        }
        return avg / timeSeries.size();
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

    private String toStr(int i, int j, List<String> currencies) {
        return "" + currencies.get(i) + SEPARATOR + currencies.get(j);
    }

    private Comparator<Map.Entry<String, Float>> createMapComparator() {
        return new Comparator<Map.Entry<String, Float>>() {
            @Override
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                int result = 0;
                if (o1 == o2) {
                  result = 0;  
                } else if (o1 != null && o2 != null) {
                    Float o1Value = o1.getValue();
                    Float o2Value = o2.getValue();
                    if (o1Value != null && o2Value != null) {
                        result = o1Value.compareTo(o2Value);
                    } else if (o1Value == null && o2Value != null) {
                       result = -1; 
                    } else if (o1Value != null && o2Value == null) {
                        result = 1;
                    } else if (o1Value == null && o2Value == null) {
                        result = 0;
                    }
                } else if (o1 == null && o2 != null) {
                    result = -1;
                } else if (o1 != null && o2 == null) {
                    result = 1;
                } else if (o1 == null && o2 == null) {
                    result = 0;
                }
                return result;
            }
        };
    }

    private Map<String, Set<String>> initGraph(List<String> currencySymbols) {
        Map<String, Set<String>> output = new HashMap<>();
        for (String currency : currencySymbols) {
            output.put(currency, new HashSet<String>());
        }
        return output;
    }

    private Set<Set<String>> initForest(List<String> currencySymbols) {
        Set<Set<String>> output = new HashSet<>();
        for (String currency : currencySymbols) {
            Set<String> tree = new HashSet<>();
            tree.add(currency);
            output.add(tree);
        }
        return output;
    }

    private Set<String> findForestContainingTree(Set<Set<String>> forest, String curr) {
        Set<String> output = new HashSet<>();
        Iterator<Set<String>> iterator = forest.iterator();
        while (iterator.hasNext()) {
            Set<String> tree = iterator.next();
            if (tree.contains(curr)) {
                output.addAll(tree);
                iterator.remove();
                break;
            }
        }
        return output;
    }
}