package com.polmos.cc.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author RobicToNieMaKomu
 */
@RunWith(MockitoJUnitRunner.class)
public class TimeUtilsTest {
    
    @InjectMocks
    private TimeUtils timeUtils = new TimeUtilsImpl();
    
    @Test
    public void contvertAliorRawDateTest() {
        String rawDate = "16.04.2014 godz. 21:04:32";
        String output = timeUtils.toISO8601(rawDate);
        String expected = "2014-05-16T21:04:32+0200";
        Assert.assertEquals(expected, output);
    }
}