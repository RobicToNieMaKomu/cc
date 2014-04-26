package com.polmos.cc.service.yahoo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public class YQLQueryBuilderImpl implements YQLQueryBuilder {

    private static final String SELECT_CLAUSE = "select * from yahoo.finance.xchange where pair in ";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String QUOTES = "\"";
    private static final String COMMA = ", ";
    
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
}
