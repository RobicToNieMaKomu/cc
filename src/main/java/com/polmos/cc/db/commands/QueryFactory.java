package com.polmos.cc.db.commands;

import static com.polmos.cc.db.commands.CommandNames.RECENT_DAY;
import static com.polmos.cc.db.commands.CommandNames.TWO_DOCS;
import static com.polmos.cc.db.commands.CommandNames.toCommandName;

import java.util.Date;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.polmos.cc.service.TimeUtils;
import com.polmos.cc.service.yahoo.annotation.Now;

public class QueryFactory {

	@Inject
	private SimpleQueryHolder holder;
	
	@Inject
	private TimeUtils timeUtils;
	
	@Inject
	@Now
	private Instance<Date> now;
	
	public Queryable produce(int inputParameter) {
		CommandNames commandName = toCommandName(inputParameter);
		return (isSimpleQuery(commandName)) ? holder.getQuery(commandName) : constructCustomQ(inputParameter);
	}

	private Queryable constructCustomQ(int days) {
		return new NDays(days, now.get(), timeUtils);
	}

	private boolean isSimpleQuery(CommandNames commandName) {
		return TWO_DOCS.equals(commandName) || RECENT_DAY.equals(commandName);
	}
}
