package com.polmos.cc.service.yahoo;

import com.mongodb.DBObject;
import com.polmos.cc.db.DAO;
import com.polmos.cc.db.DBUtils;
import com.polmos.cc.rest.RESTClient;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.inject.Instance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author RobicToNieMaKomu
 */
@RunWith(MockitoJUnitRunner.class)
public class YahooFxDownloaderTest {

    @Mock
    private Instance<Date> now;
    @Mock
    private RESTClient restClient;
    @Mock
    private YQLQueryBuilder yqLQueryBuilder;
    @Mock
    private DBUtils dBUtils;
    @Mock
    private DAO dao;
    @InjectMocks
    private Runnable fxDownloader = new YahooFxDownloader();

    @Before
    public void setup() {
        Mockito.when(dao.createDocument(Mockito.any(DBObject.class))).thenReturn(true);
    }

    @Test
    public void before8amTest() {
        Mockito.when(now.get()).thenReturn(createDate(2, 10, 7));
        fxDownloader.run();
        Mockito.verifyZeroInteractions(dao);
    }

    @Test
    public void after8amTest() {
        Mockito.when(now.get()).thenReturn(createDate(2, 1, 8));
        fxDownloader.run();
        Mockito.verify(dao, Mockito.times(1)).createDocument(Matchers.any(DBObject.class));
    }

    @Test
    public void before4pmTest() {
        Mockito.when(now.get()).thenReturn(createDate(2, 55, 15));
        fxDownloader.run();
        Mockito.verify(dao, Mockito.times(1)).createDocument(Matchers.any(DBObject.class));
    }

    @Test
    public void after4pmTest() {
        Mockito.when(now.get()).thenReturn(createDate(2, 1, 16));
        fxDownloader.run();
        Mockito.verifyZeroInteractions(dao);
    }

    @Test
    public void onSaturdayTest() {
        Mockito.when(now.get()).thenReturn(createDate(7, 10, 10));
        fxDownloader.run();
        Mockito.verifyZeroInteractions(dao);
    }

    @Test
    public void onSundayTest() {
        Mockito.when(now.get()).thenReturn(createDate(1, 10, 10));
        fxDownloader.run();
        Mockito.verifyZeroInteractions(dao);
    }

    @Test
    public void yahooServiceDidntRespondTest() {
    }

    private Date createDate(int dayOfWeek, int minute, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }
}
