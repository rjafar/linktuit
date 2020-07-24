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
            
            // create a database entry including [_id, originalURL, numReqsLeft]
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
        // search database by the specific id associated with the id sequence
        BasicDBObject query = new BasicDBObject();
        query.put("_id", name);

        // increment the sequence each time a new entry is added
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", 1));
        DBObject obj = col.findAndModify(query, update);

        // return the updated sequence number to serve as the id for an entry
        return (long)obj.get("seq");
    }

    /**
     * This method searches the database for the entry at the index provided
     * provided by the parameter, id. It retrieves the original URL.
     * @param long id
     * @return String original URL
     */
    public String findMapping(long id) {
        try {
            // set up mongo client and get the specific database and collection
            MongoClient client = new MongoClient("localhost", 27017);
            DB database = client.getDB("linktuit_db");
            DBCollection urlMappings = database.getCollection("url_mappings");
            
            // search database by id and decrement the number of requests
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            BasicDBObject update = new BasicDBObject();
            update.put("$inc", new BasicDBObject("numReqsLeft", -1));
            DBObject entry = urlMappings.findAndModify(query, update);
            
            if (entry != null) {
                // get the original URL from the db entry
                String originalUrl = entry.get("originalURL").toString();

                // delete entry from db if there are no more requests left
                if (entry.get("numReqsLeft") != null && (int)entry.get("numReqsLeft") <= 0){
                    deleteMapping(id, urlMappings);
                    System.out.println("Link to " + originalUrl + " has expired!");
                    return "ERR: Link expired";
                }
                else {
                   return originalUrl; 
                }  
            } 
               
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "ERR";
    }

    /**
     * This method deletes an entry from the db.
     * @param long id of the entry to be deleted
     * @param DBCollection the collection to delete from
     */
    public void deleteMapping(long id, DBCollection col) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        col.remove(query);
        System.out.println("Entry deleted!");
    }
}