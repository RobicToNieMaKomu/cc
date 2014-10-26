package com.polmos.cc.db.commands;

import static com.polmos.cc.db.commands.CommandNames.RECENT_DAY;
import static com.polmos.cc.db.commands.CommandNames.TWO_DOCS;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.polmos.cc.service.TimeUtils;

public class SimpleQueryHolder {

	@Inject
	private TimeUtils timeUtils;
	
	private Map<CommandNames, Queryable> commands;

	@PostConstruct
	public void initialize() {
		commands = new EnumMap<>(CommandNames.class);
		commands.put(TWO_DOCS, new TwoRecentDocuments());
		commands.put(RECENT_DAY, new LastDay(timeUtils));
	}
	
	public Queryable getQuery(CommandNames commandName) {
		return commands.get(commandName);
	}
}