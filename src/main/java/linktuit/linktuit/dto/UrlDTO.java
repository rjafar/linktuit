package linktuit.linktuit.dto;

import java.util.Date;

/**
 * This class represents a URL Data Transfer Object (DTO) which is the request
 * made each time a short link is clicked.
 */
public class UrlDTO {
    private String originalUrl;
    private Date createdDate;
    private int numRequestsLeft;

    public UrlDTO(String originalUrl, Date createdDate, int numRequestsLeft) {
        this.originalUrl = originalUrl;
        this.createdDate = createdDate;
        this.numRequestsLeft = numRequestsLeft;
    }

    /**
     * Return the original long URL
     * @return String original URL
     */
    public String getOriginalUrl() {
        return this.originalUrl;
    }

    /**
     * Save the original long URL
     * @param originalUrl
     */
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    /**
     * Returns the date that this data request was created
     * @return Date that this data request was created
     */
    public Date getCreatedDate() {
        return this.createdDate;
    }

    /**
     * Saves the date that the data request was created
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the number of requests left until this link is expired
     * @return int number of requests left
     */
    public int getNumReqsLeft() {
        return this.numRequestsLeft;
    }

    /**
     * Sets the number of requests left to this link
     * @param numReqsLeft
     */
    public void setNumReqsLeft(int numReqsLeft) {
        this.numRequestsLeft = numReqsLeft;
    }
}
