package linktuit.linktuit.service;

// Spring packages
import org.springframework.stereotype.Service;

// Java utility packages
import java.util.Date;

// Internal classes
import linktuit.linktuit.modal.UrlMapping;
import linktuit.linktuit.dao.UrlDAO;
import linktuit.linktuit.dto.UrlDTO;
import linktuit.linktuit.service.Converter;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlDAO urlDao;
    private final Converter converter;

    /**
     * Constructor
     * @param urlDao
     * @param converter
     */
    public UrlServiceImpl(UrlDAO urlDao, Converter converter) {
        this.urlDao = urlDao;
        this.converter = converter;
    }

    /**
     * Creates new URL mapping that will be saved into the database then
     * takes the unique id from the database and converts it to the short URL
     */
    @Override
    public String shortenURL(UrlDTO dtoRequest) {
        // create a new URL mapping
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalURL(dtoRequest.getOriginalUrl());
        mapping.setNumRequestsLeft(dtoRequest.getNumReqsLeft());
        mapping.setCreatedDate(dtoRequest.getCreatedDate());

        // save mapping as entry in database and grab the unique ID
        long id = urlDao.save(mapping);

        if (id == -1) {
            return "ERR: Could not save to db";
        }
        
        // encode the id in order to use as the short URL
        String shortUrl = converter.base10ToBase62(id);

        return shortUrl;
    }

    /**
     * Converts the short URL back to the base 10 ID which is used to lookup
     * in the database the mapping to the original URL. Deletes database entries 
     * if the number of requests alotted has reached 0.
     */
    @Override
    public String getLongURL(String shorturl) {
        // convert short URL to get back the id
        long id = converter.base62ToBase10(shorturl);

        try {
            // find database entry indexed by id
            String originalURL = urlDao.findMapping(id);
            return originalURL;
        } catch (Exception ex) {
            System.out.println("" + ex + "- No mapping exists for linktuit.com/" + shorturl);
            return "ERR";
        }
    }
}