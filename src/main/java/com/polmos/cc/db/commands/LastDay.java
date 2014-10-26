package com.polmos.cc.db.commands;

import static com.polmos.cc.db.DBConstants.MAX_DAILY_DOC_COUNT;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jboss.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.polmos.cc.db.DBQueryBuilder;
import com.polmos.cc.service.TimeUtils;

public class LastDay implements Queryable {

	private static final Logger logger = Logger.getLogger(LastDay.class);
	
	private final TimeUtils timeUtils;
	
	public LastDay(TimeUtils timeUtils) {
		this.timeUtils = timeUtils;
	}
	
	@Override
	public List<DBObject> execute(DBCollection collection) {
		Date now = new Date();
		Date from = resetHourAndMinutes(now);
		
		String toDate = timeUtils.toISO8601(now);
		String fromDate = timeUtils.toISO8601(from);
		
		logger.info("FromDate:" + fromDate + ", toDate:" + toDate);
		
		BasicDBObject query = DBQueryBuilder.fromToQuery(fromDate, toDate);
		DBCursor cursor = collection.find(query).sort(new BasicDBObject("creationTime", -1))
				.limit(MAX_DAILY_DOC_COUNT);
		
		logger.info("cursor size:" + cursor.size());
		return new ArrayList<>(cursor.toArray());
	}

	private Date resetHourAndMinutes(Date now) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.clear();
		calendar.setTime(now);
		calendar.set(GregorianCalendar.MINUTE, 0);
		calendar.set(GregorianCalendar.HOUR, 0);
		return calendar.getTime();
	}
}
