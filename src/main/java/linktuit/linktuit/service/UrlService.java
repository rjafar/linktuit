package linktuit.linktuit.service;

// internal classes
import linktuit.linktuit.dto.UrlDTO;

/**
 * Interface that defines the methods for the UrlService: shortenURL
 * and getLongURL
 */
public interface UrlService {

    String shortenURL(UrlDTO longurl);

    String getLongURL(String shorturl);
}