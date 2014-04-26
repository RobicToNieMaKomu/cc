package com.polmos.cc.service.alior;

import com.polmos.cc.constants.Constants;
import com.polmos.cc.service.TimeUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
public class AliorCsvParserImpl implements AliorCsvParser {

    private static final Logger logger = Logger.getLogger(AliorCsvParserImpl.class);
    private static final String CRLN_REG = "\r\n";
    private static final String EMPTY_LINE_REG = "^\\s*$";
    private static final String TITLE = "Kursy walut";
    private static final String UPDATE_TIME_LINE = "Aktualizacja";
    private static final String DESC_LINE = "Kraj";
    private static final String SEMI_COLON = ";";
    private static final String COMMA = ",";
    private static final String DOT = ".";
    private static final int EXPECTED_ROW_SIZE = 7;
    @Inject
    private TimeUtils timeUtils;

    public AliorCsvParserImpl() {
    }

    @Override
    public JsonObject parseRawText(String csv) throws IOException {
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        logger.info("Parsing raw text:\n" + csv);
        if (csv != null) {
            try {
                String[] lines = csv.split(CRLN_REG);
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    if (line == null || line.matches(EMPTY_LINE_REG) || line.isEmpty()
                            || line.contains(TITLE) || line.contains(DESC_LINE)) {
                        continue;
                    } else if (line.contains(UPDATE_TIME_LINE)) {
                        line = line.replaceAll(UPDATE_TIME_LINE + "\\s", "");
                        String updateTime = timeUtils.toISO8601(line);
                        objBuilder.add(Constants.CREATION_TIME_PROPERTY, updateTime);
                    } else {
                        String[] row = line.split(SEMI_COLON);
                        if (EXPECTED_ROW_SIZE == row.length) {
                            JsonObjectBuilder builder = Json.createObjectBuilder();
                            BigDecimal buy = new BigDecimal(row[3].replace(COMMA, DOT));
                            BigDecimal sell = new BigDecimal(row[4].replace(COMMA, DOT));
                            BigDecimal spread = new BigDecimal(row[5].replace(COMMA, DOT));
                            builder.add(Constants.BUY_PROPERTY, buy);
                            builder.add(Constants.SELL_PROPERTY, sell);
                            builder.add(Constants.SPREAD_PROPERTY, spread);
                            objBuilder.add(row[2], builder.build());
                        } else {
                            logger.warn("Unexpected row:" + row);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Couldnt parse string containing exchange rates:" + csv, e);
                throw new IOException("CSV parsing error", e);
            }
        }
        return objBuilder.build();
    }
}
