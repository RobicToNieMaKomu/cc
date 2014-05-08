package com.polmos.cc.service.yahoo;

import java.util.List;
import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface YQLQueryBuilder {

    String constructSelectQuery(Set<String> currencyPairs);
    
    Set<String> addBaseCurrencyToEachElement(List<String> currencies);
}
