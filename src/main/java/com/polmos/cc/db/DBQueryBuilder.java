package com.polmos.cc.db;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;

public class DBQueryBuilder {

	public static BasicDBObject fromToQuery(String from, String to) {
		BasicDBObject query = new BasicDBObject();
		query.put("creationTime",
				BasicDBObjectBuilder.start("$gte", from).add("$lte", to).get());
		return query;
	}
}
