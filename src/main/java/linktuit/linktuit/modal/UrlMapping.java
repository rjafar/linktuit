package linktuit.linktuit.modal;

// Java utility packages
import java.util.Date;

/**
 * The UrlMapping class represents the entries that will be saved in the database.
 * Each entry will have a unique ID that is supplied by the database, the original
 * long URL, and the number of requests left until the entry expires. 
 */
public class UrlMapping {

    private long id;
    private String originalURL;
    private int numRequestsLeft; // initially 10
    private Date createdDate;

    /**
     * The getId method retrieves the unique ID from the database that maps the original
     * URL to its shortened version.
     * @param None
     * @return long ID
     */
    public long getId() {
        return id;
    }

    /**
     * The setId method saves the unique ID from the database to its URL mapping.
     * @param long ID
     * @return None
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the original long URL.
     * @param None
     * @return String original long URL
     */
    public String getOriginalURL() {
        return originalURL;
    }

    /**
     * Sets the original long URL.
     * @param String original long URL
     * @return None
     */
    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    /**
     * Get the number of requests left to this link. The link is considered expired
     * when numRequests == 0 and the mapping entry should be deleted from the database.
     * @param None
     * @return int number of requests left before link expires. Must be > 0.
     */
    public int getNumRequestsLeft() {
        return numRequestsLeft;
    }

    /**
     * Set the number of requests left before the short link expires.
     * @param int number of requests left
     * @return None
     */
    public void setNumRequestsLeft(int numRequestsLeft) {
        this.numRequestsLeft = numRequestsLeft;
    }

    /**
     * Returns the date the request was created
     * @return Date 
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date the request was created
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}