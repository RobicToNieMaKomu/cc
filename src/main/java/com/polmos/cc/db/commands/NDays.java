package com.polmos.cc.db.commands;

import static com.polmos.cc.db.DBConstants.MAX_DAILY_DOC_COUNT;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.polmos.cc.db.DBConstants;
import com.polmos.cc.db.DBQueryBuilder;
import com.polmos.cc.service.TimeUtils;

public class NDays implements Queryable {

	private static final Logger logger = Logger.getLogger(NDays.class);

	
	private final int daysBeforeThreshold;
	private final Date threshold;
	private final TimeUtils timeUtils;

	public NDays(int daysBeforeThreshold, Date threshold, TimeUtils timeUtils) {
		this.daysBeforeThreshold = daysBeforeThreshold;
		this.threshold = requireNonNull(threshold);
		this.timeUtils = requireNonNull(timeUtils);
	}

	@Override
	public List<DBObject> execute(DBCollection collection) {
		List<DBObject> result = new ArrayList<>();
		
		for (int i = 0; i < daysBeforeThreshold; i++) {
			Date from = new Date(threshold.getTime() - (i + 1)
					* TimeUnit.DAYS.toMillis(1));
			Date to = new Date(from.getTime() + TimeUnit.DAYS.toMillis(1));
			result.addAll(documentsFromTo(from, to, collection));
		}
		
		logger.info("Num of queried documents:" + result.size());
		return result;
	}

	public int getDaysBeforeThreshold() {
		return daysBeforeThreshold;
	}

	public Date getThreshold() {
		return new Date(threshold.getTime());
	}

	private List<DBObject> documentsFromTo(Date from, Date to, DBCollection collection) {
		String fromDate = timeUtils.toISO8601(from);
		String toDate = timeUtils.toISO8601(to);
		
		logger.info("FromDate:" + fromDate + ", toDate:" + toDate);
		
		BasicDBObject query = DBQueryBuilder.fromToQuery(fromDate, toDate);
		DBCursor cursor = collection.find(query).sort(new BasicDBObject("creationTime", -1))
				.limit(MAX_DAILY_DOC_COUNT);
		
		logger.info("cursor size:" + cursor.size());

		return new ArrayList<>(cursor.toArray());
	}
}
