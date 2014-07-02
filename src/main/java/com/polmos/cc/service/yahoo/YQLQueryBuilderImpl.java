package com.polmos.cc.service.yahoo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public class YQLQueryBuilderImpl implements YQLQueryBuilder {

    private static final String SELECT_CLAUSE = "select id, Rate, Ask, Bid from yahoo.finance.xchange where pair in ";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String QUOTES = "\"";
    private static final String COMMA = ", ";
    private static final String BASE_CURRENCY = "PLN";
    
    @Override
    public String constructSelectQuery(Set<String> currencyPairs) {
        StringBuilder builder = new StringBuilder();
        if (currencyPairs != null && !currencyPairs.isEmpty()) {
            builder.append(SELECT_CLAUSE);
            builder.append(LEFT_BRACKET);
            Iterator<String> iterator = currencyPairs.iterator();
            while (iterator.hasNext()) {
                builder.append(QUOTES);
                builder.append(iterator.next());
                builder.append(QUOTES);
                if (iterator.hasNext()) {
                    builder.append(COMMA);
                }
            }
            builder.append(RIGHT_BRACKET);
        }
        return builder.toString();
    }

    @Override
    public Set<String> addBaseCurrencyToEachElement(List<String> currencies) {
        Set<String> output = new HashSet<>();
        if (currencies != null) {
            for (String currency : currencies) {
                output.add(BASE_CURRENCY + currency);
            }
        }
        return output;
    }
}