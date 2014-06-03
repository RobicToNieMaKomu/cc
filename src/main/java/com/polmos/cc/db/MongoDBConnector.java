package com.polmos.cc.db;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.polmos.cc.constants.Constants;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

/**
 *
 * @author RobicToNieMaKomu
 */
@ApplicationScoped
public class MongoDBConnector {

    private static final Logger logger = Logger.getLogger(MongoDBConnector.class);
    private static final String DB_NAME = "cc";
    
    private volatile Mongo mongo;
    private volatile DB mongoDB;
    
    public MongoDBConnector() throws IOException {
        init();
    }
    
    private void init() throws IOException {
	String host = System.getenv(Constants.MONGODB_HOST_PROPERTY);
        String portProp = System.getenv(Constants.MONGODB_PORT_PROPERTY);
        String db = System.getenv(Constants.APP_NAME_PROPERTY);
        String user = System.getenv(Constants.MONGODB_USERNAME_PROPERTY);
        String password = System.getenv(Constants.MONGODB_PASSWORD_PROPERTY);

        int port = Integer.decode(portProp);
        if(db == null) {
            db = DB_NAME;
        }
        try {
            logger.info("MONGODB INITIALIZATION");
            logger.info("host:" + host);
            logger.info("port:" + portProp);
            logger.info("db:" + db);
            logger.info("user:" + user);
            logger.info("password:" + password);
            mongo = new Mongo(host, port);
        } catch (UnknownHostException e) {
            throw new IOException("Failed to access Mongo server", e);
        }
        mongoDB = mongo.getDB(db);
        if(mongoDB.authenticate(user, password.toCharArray()) == false) {
            throw new IOException("Failed to authenticate against db: "+db);
        }
    }
    
    public DB getDB() {
        return mongoDB;
    }
}