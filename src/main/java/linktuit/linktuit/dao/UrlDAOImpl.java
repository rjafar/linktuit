package linktuit.linktuit.dao;

// Spring packages
import org.springframework.stereotype.Repository;

import linktuit.linktuit.modal.UrlMapping;

// MongoDB packages
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.DBObject;

@Repository
public class UrlDAOImpl implements UrlDAO {

    /**
     * This function saves the information from the UrlMapping object such as
     * the original URL, number of requests left to the link, and a unique sequential id.
     * @param URLMapping
     * @return long id of newly saved database entry 
     */
    public long save(UrlMapping urlMapping) 
    {
        // set up mongo client and get the specific database and collection
        try {
            MongoClient client = new MongoClient("localhost", 27017);
            DB database = client.getDB("linktuit_db");
            DBCollection urlMappings = database.getCollection("url_mappings");
            
            // create a database entry including [_id, original_URL, num_requests_left]
            BasicDBObject dbEntry = new BasicDBObject();
            long id = getNextId("urlid", urlMappings);
            dbEntry.put("_id", id);
            dbEntry.put("originalURL", urlMapping.getOriginalURL());
            dbEntry.put("numReqsLeft", urlMapping.getNumRequestsLeft());

            // insert entry into db collection
            urlMappings.insert(dbEntry);

            // return the id associated with the newly created db entry
            return id;
        } catch (Exception ex) {
            System.out.println(ex);
            return -1;
        }
    }

    /**
     * This function increments the sequence id
     * @param String name of entry we want to update, urlid
     * @param DBCollection the collection we want to look for "name" in
     * @return long id
     */
    public long getNextId(String name, DBCollection col) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", 1));
        DBObject obj = col.findAndModify(query, update);
        return (long)obj.get("seq");
    }

    public String findMapping(long id) {
        // set up mongo client and get the specific database and collection
        try {
            MongoClient client = new MongoClient("localhost", 27017);
            DB database = client.getDB("linktuit_db");
            DBCollection urlMappings = database.getCollection("url_mappings");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            BasicDBObject update = new BasicDBObject();
            update.put("$inc", new BasicDBObject("numRequestsLeft", -1));
            DBObject entry = urlMappings.findAndModify(query, update);
            
            if (entry != null) {
                String originalUrl = entry.get("originalURL").toString();
                if (entry.get("numRequestsLeft") != null && (int)entry.get("numRequestsLeft") <= 0){
                    deleteMapping(entry, urlMappings);
                    System.out.println("Link to " + originalUrl + " has expired!");
                }
                return originalUrl;
            }
               
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "ERR";
    }

    public void deleteMapping(DBObject entry, DBCollection col) {
        col.remove(entry);
    }
}