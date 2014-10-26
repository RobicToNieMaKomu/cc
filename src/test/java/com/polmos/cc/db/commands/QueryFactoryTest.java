package com.polmos.cc.db.commands;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.enterprise.inject.Instance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.polmos.cc.service.TimeUtils;

@RunWith(MockitoJUnitRunner.class)
public class QueryFactoryTest {

	@Spy
	private SimpleQueryHolder holder;
	
	@Mock
	private Instance<Date> now;
	
	@Mock
	private TimeUtils timeUtils;
	
	@InjectMocks
	private QueryFactory factory;
	
	@Before
	public void setup() {
		holder.initialize();
	}
	
	@Test
	public void produceTwoRecentDocsQuery() {
		Queryable query = factory.produce(0);
		
		assertTrue(query instanceof TwoRecentDocuments);
	}
	
	@Test
	public void producePreviousDayQuery() {
		Queryable query = factory.produce(1);
		
		assertTrue(query instanceof LastDay);
	}
	
	@Test
	public void produceNDaysQuery() {
		when(now.get()).thenReturn(new Date());
		
		Queryable query = factory.produce(2);
		
		assertTrue(query instanceof NDays);
	}
	
	@Test
	public void getLastThreeDaysQuery() {
		Date to = new Date();
		int daysBefore = 3;
		
		when(now.get()).thenReturn(to);
		
		Queryable query = factory.produce(daysBefore);
		
		Assert.assertEquals(daysBefore, ((NDays) query).getDaysBeforeThreshold());
		Assert.assertEquals(to, ((NDays) query).getThreshold());
	}
}
