package com.polmos.cc.db.commands;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class TwoRecentDocuments implements Queryable{

	@Override
	public List<DBObject> execute(DBCollection collection) {
		return collection.find().sort(new BasicDBObject("_id", -1)).limit(2).toArray();
	}
}

/*
Projection is not working like this yet!!!
https://jira.mongodb.org/browse/SERVER-831
BasicDBObject query = new BasicDBObject("query.lang", "en-US");

BasicDBObject in = new BasicDBObject();
in.put("$in", currencies);

BasicDBObject id = new BasicDBObject();
id.put("id", in);

BasicDBObject elemMatch = new BasicDBObject();
elemMatch.put("$elemMatch", id);

BasicDBObject projection = new BasicDBObject();
projection.put("_id", 0);
projection.put("query.results.rate", elemMatch);

logger.info("query:" + query.toMap().toString());
logger.info("projection:" + projection.toMap().toString());
DBCursor cursor = collection.find(query, projection).sort(new BasicDBObject("_id", -1)).limit(2);
*/