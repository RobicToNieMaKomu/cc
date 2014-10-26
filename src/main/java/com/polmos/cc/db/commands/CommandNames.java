package com.polmos.cc.db.commands;

public enum CommandNames {
	TWO_DOCS, RECENT_DAY, PREVIOUS_DAYS;

	public static CommandNames toCommandName(int requestInput) {
		CommandNames result = null;
		
		switch (requestInput) {
		case 0:
			result = TWO_DOCS;
			break;
		case 1:
			result = RECENT_DAY;
			break;
		default:
			result = PREVIOUS_DAYS;
			break;
		}

		return result;
	}

}
