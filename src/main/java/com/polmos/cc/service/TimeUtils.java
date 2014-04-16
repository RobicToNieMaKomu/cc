package com.polmos.cc.service;

import java.util.Date;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface TimeUtils {

    String toISO8601(String aliorDate);

    String toISO8601(Date date);
}
