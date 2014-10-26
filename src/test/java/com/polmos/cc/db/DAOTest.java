package com.polmos.cc.db;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;

import java.util.Date;

import javax.enterprise.inject.Instance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.polmos.cc.db.commands.Queryable;
import com.polmos.cc.db.commands.SimpleQueryHolder;
import com.polmos.cc.service.TimeUtils;
import com.polmos.cc.service.TimeUtilsImpl;

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
	@Mock
	private DB db;
	@Mock
	private MongoDBConnector connector;

	@Mock
	private SimpleQueryHolder queryHolder;

	@Mock
	private Queryable query;

	@InjectMocks
	private DAO dao = new DAOImpl();

	@Before
	public void setup() {
		Mockito.when(connector.getDB()).thenReturn(db);
	}

	@Test
	public void checkIfTransactionIsOpen() {
		dao.getDocuments(query);

		Mockito.verify(db, times(1)).requestStart();
	}

	@Test
	public void checkIfTransactionIsClosed() {
		dao.getDocuments(query);

		Mockito.verify(db, times(1)).requestDone();
	}

	@Test
	public void verifyIfQueryIsExecuted() {
		dao.getDocuments(query);

		Mockito.verify(query, times(1)).execute((DBCollection) anyObject());
	}

	@Test
	public void checkIfTransactionIsClosedEvenIfRuntimeIsThrown() {
		Mockito.when(db.getCollection(Mockito.anyString())).thenThrow(
				RuntimeException.class);
		try {
			dao.getDocuments(query);
		} catch (RuntimeException expectedException) {
		}
		Mockito.verify(db, times(1)).requestDone();
	}
}
