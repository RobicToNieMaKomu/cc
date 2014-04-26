package com.polmos.cc.service.yahoo;

import java.util.List;

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
    public String constructSelectQuery(List<String> currencyPairs) {
        StringBuilder builder = new StringBuilder();
        if (currencyPairs != null && !currencyPairs.isEmpty()) {
            builder.append(SELECT_CLAUSE);
            builder.append(LEFT_BRACKET);
            for (int i = 0; i < currencyPairs.size(); i++) {
                builder.append(QUOTES);
                builder.append(currencyPairs.get(i));
                builder.append(QUOTES);
                if (i != currencyPairs.size() - 1) {
                    builder.append(COMMA);
                }
            }
            builder.append(RIGHT_BRACKET);
        }
        return builder.toString();
    }
}
