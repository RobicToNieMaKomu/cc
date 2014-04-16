package com.polmos.cc.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author RobicToNieMaKomu
 */
public class TimeUtilsImpl implements TimeUtils {

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String HOUR_SEPARATOR = "godz.";
    private static final String DOT_REGEX = "\\.";
    private static final String COLON = ":";
    private final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Override
    public String toISO8601(Date date) {
        String output = null;
        if (date != null) {
            try {
                output = formater.format(date);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return output;
    }

    // 16.04.2014 godz. 21:04:32
    @Override
    public String toISO8601(String aliorDate) {
        String output = null;
        if (aliorDate != null) {
            try {
                String rawDate = aliorDate.replaceAll(HOUR_SEPARATOR, DOT_REGEX).replaceAll(COLON, DOT_REGEX).trim().replaceAll(WHITESPACE_REGEX, "");
                String[] dArray = rawDate.split(DOT_REGEX);
                if (dArray.length == 6) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    int year = Integer.parseInt(dArray[2]);
                    int month = Integer.parseInt(dArray[1]);
                    int day = Integer.parseInt(dArray[0]);
                    int hourOfDay = Integer.parseInt(dArray[3]); // UTC
                    int minute = Integer.parseInt(dArray[4]);
                    int second = Integer.parseInt(dArray[5]);
                    calendar.set(year, month, day, hourOfDay, minute, second);
                    Date decentDate = calendar.getTime();
                    output = formater.format(decentDate);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return output;
    }
}
