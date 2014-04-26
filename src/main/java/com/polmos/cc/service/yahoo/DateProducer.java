package com.polmos.cc.service.yahoo;

import com.polmos.cc.service.yahoo.annotation.Now;
import java.util.Date;
import javax.enterprise.inject.Produces;

/**
 *
 * @author RobicToNieMaKomu
 */
/**  Used mostly as a factory of dates that can be mocked during tests */
public class DateProducer {

    @Produces
    @Now
    public Date now() {
        return new Date();
    }
}
