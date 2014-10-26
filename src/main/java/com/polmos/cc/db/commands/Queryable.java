package com.polmos.cc.db.commands;

import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public interface Queryable {

	List<DBObject> execute(DBCollection collection);
}
