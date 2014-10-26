package com.polmos.cc.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.polmos.cc.db.commands.Queryable;
import com.polmos.cc.service.TimeUtils;
import com.polmos.cc.service.yahoo.annotation.Now;

/**
 *
 * @author RobicToNieMaKomu
 */
public class DAOImpl implements DAO {

	private static final Logger logger = Logger.getLogger(DAOImpl.class);
	
	private static final String EXCHANGE_RATES_COLLECTION = "rates";
	@Inject
	private MongoDBConnector mongoDBConnector;
	@Inject
	private TimeUtils timeUtils;
	@Inject
	@Now
	private Instance<Date> now;
	
	@Override
	public List<DBObject> getDocuments(Queryable query) {
		DB db = mongoDBConnector.getDB();
		try {
			db.requestStart();
			return query.execute(db.getCollection(EXCHANGE_RATES_COLLECTION));
		} finally {
			db.requestDone();
		}
	}

	/*
	 * @Override public List<DBObject> getDocuments(int days, List<String>
	 * currencies) { List<DBObject> documents = new ArrayList<>(); if (days ==
	 * 0) { documents = getRecentTwoDocuments(currencies); } else if (days > 0)
	 * { for (int i = 0; i < days; i++) { Date from = new
	 * Date(now.get().getTime() - (i + 1) * TimeUnit.DAYS.toMillis(1)); Date to
	 * = new Date(from.getTime() + TimeUnit.DAYS.toMillis(1));
	 * documents.addAll(documentsFromTo(from, to)); } }
	 * logger.info("Num of requested documents:" + documents.size()); return
	 * documents; }
	 */
	// @Override
	public List<DBObject> getAllDocuments() {
		List<DBObject> output = new ArrayList<>();
		logger.debug("Getting all documents from db");
		DB db = mongoDBConnector.getDB();
		try {
			db.requestStart();
			DBCollection rates = db.getCollection(EXCHANGE_RATES_COLLECTION);
			logger.debug("DB returned collection:" + rates);
			DBCursor iterator = rates.find();
			while (iterator.hasNext()) {
				DBObject nextItem = iterator.next();
				logger.debug("Next dbObject:" + nextItem);
				output.add(nextItem);
			}
		} finally {
			db.requestDone();
		}
		return output;
	}

	@Override
	public boolean createDocument(DBObject data) {
		boolean success = false;
		if (data != null) {
			DB db = mongoDBConnector.getDB();
			try {
				db.requestStart();
				DBCollection rates = db
						.getCollection(EXCHANGE_RATES_COLLECTION);
				rates.insert(data);
			} finally {
				db.requestDone();
			}
		}
		return success;
	}

	@Override
	public int removeBefore(Date date) {
		int removed = 0;
		DB db = mongoDBConnector.getDB();
		db.requestStart();
		try {
			BasicDBObject query = new BasicDBObject();
			query.put("creationTime",
					new BasicDBObject("$lte", timeUtils.toISO8601(date)));
			logger.info("removeBefore query:" + query.toString());
			removed = db.getCollection(EXCHANGE_RATES_COLLECTION).remove(query)
					.getN();
		} finally {
			db.requestDone();
		}
		return removed;
	}
}
