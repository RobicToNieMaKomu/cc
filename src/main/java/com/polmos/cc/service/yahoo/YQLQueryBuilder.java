package com.polmos.cc.service.yahoo;

import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface YQLQueryBuilder {

    String constructSelectQuery(List<String> currencyPairs);
}
