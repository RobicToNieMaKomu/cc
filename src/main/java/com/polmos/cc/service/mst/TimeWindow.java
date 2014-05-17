package com.polmos.cc.service.mst;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public class TimeWindow {

    private final List<ExRate> exRates;
    
    public TimeWindow(List<ExRate> exRates) {
        this.exRates = (exRates != null) ? new ArrayList<>(exRates) : new ArrayList<ExRate>();
    }
    
    public List<ExRate> getExRates() {
        return new ArrayList<>(exRates);
    }
    
    public ExRate forCurrency(String currency) {
        ExRate output = null;
        for (ExRate exRate : exRates) {
            if (exRate != null && exRate.getId().contains(currency)) {
                output = exRate;
                break;
            }
        }
        return output;
    }
}
