package com.polmos.cc.service.yahoo;

import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface YQLQueryBuilder {

    String constructSelectQuery(Set<String> currencyPairs);
}
