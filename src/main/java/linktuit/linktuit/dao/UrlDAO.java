package linktuit.linktuit.dao;

// MongoDB packages
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.DBObject;

// internal classes
import linktuit.linktuit.modal.UrlMapping;

/**
 * Interface that defines the methods that interact with the database.
 */
public interface UrlDAO {

    long save(UrlMapping urlMapping);

    long getNextId(String name, DBCollection col);

    String findMapping(long id);

    void deleteMapping(DBObject entry, DBCollection col);
}

