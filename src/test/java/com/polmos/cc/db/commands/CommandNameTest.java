package com.polmos.cc.db.commands;

import static com.polmos.cc.db.commands.CommandNames.PREVIOUS_DAYS;
import static com.polmos.cc.db.commands.CommandNames.RECENT_DAY;
import static com.polmos.cc.db.commands.CommandNames.TWO_DOCS;
import static com.polmos.cc.db.commands.CommandNames.toCommandName;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandNameTest {
	
	@Test
	public void zeroAsTwoDocuments() {
		assertEquals(TWO_DOCS, toCommandName(0));
	}
	
	@Test
	public void oneAsRecentDay() {
		assertEquals(RECENT_DAY, toCommandName(1));
	}
	
	@Test
	public void randomInputAsPreviousDays() {
		Random random = new Random();
		
		assertEquals(PREVIOUS_DAYS, toCommandName(1 + random.nextInt()));
	}
}
