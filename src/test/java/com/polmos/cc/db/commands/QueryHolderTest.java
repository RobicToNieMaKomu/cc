package com.polmos.cc.db.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QueryHolderTest {

	@Mock
	private TwoRecentDocuments twoRecentDocumentsQuery;
	
	@Mock
	private LastDay recentDayQuery;
	
	@Mock
	private NDays previousDaysQuery;
	
	@InjectMocks
	private SimpleQueryHolder queryHolder;
	
	@Before
	public void setup() {
		queryHolder.initialize();
	}
	
	@Test
	public void getLastTwoDocumentsQuery() {
		assertEquals(twoRecentDocumentsQuery, queryHolder.getQuery(CommandNames.TWO_DOCS));
	}
	
	@Test
	public void getRecentDayQuery() {
		assertEquals(recentDayQuery, queryHolder.getQuery(CommandNames.RECENT_DAY));
	}
}
