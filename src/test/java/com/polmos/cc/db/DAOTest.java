package com.polmos.cc.db;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.polmos.cc.service.TimeUtils;
import com.polmos.cc.service.TimeUtilsImpl;
import java.util.Date;
import java.util.List;
import javax.enterprise.inject.Instance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author RobicToNieMaKomu
 */
@RunWith(MockitoJUnitRunner.class)
public class DAOTest {

    @Mock
    private DBCollection collection;
    @Mock
    private DBCursor cursor;
    @Spy
    private TimeUtils timeUtils = new TimeUtilsImpl();
    @Mock
    private Instance<Date> dateService;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private DB db;
    @Mock
    private MongoDBConnector connector;
    @InjectMocks
    private DAO dao = new DAOImpl();

    @Test
    public void getDocsFromLastTwoDaysTest() {
//        Date now = new Date();
//        Mockito.when(cursor.limit(Matchers.anyInt())).thenReturn(cursor);
//        Mockito.when(cursor.sort(Matchers.any(DBObject.class))).thenReturn(cursor);
//        Mockito.when(collection.find()).thenReturn(cursor);
//        Mockito.when(db.getCollection(Matchers.anyString())).thenReturn(collection);
//        Mockito.when(dateService.get()).thenReturn(now);
//        Mockito.when(connector.getDB()).thenReturn(db);
//        List<DBObject> out = dao.getDocuments(2);
//        Mockito.verify(db, Mockito.times(6));
    }
}
