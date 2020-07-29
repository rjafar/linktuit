package linktuit.linktuit.controller;

// Spring packages
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Java packages
import java.net.URI;
import java.util.*;

// Internal classes
import linktuit.linktuit.dto.UrlDTO;
import linktuit.linktuit.service.UrlService;

/**
 * This controller class serves as the mediator between the user
 * and the rest of the service. Contains the HTTP request methods. 
 */
@RestController
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService; 

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * Post method that creates the new shortened URL
     * @param longurl
     * @return String short URL
     */
    @PostMapping("/shortenUrl")
    public Map<String,String> shortenUrl(@RequestBody UrlDTO longurl) {
        String url = urlService.shortenURL(longurl);
        return Collections.singletonMap("url", url);
    }

    /**
     * Get request that gets the long URL and redirects to it
     * @param shortUrl
     * @return Response
     */
    @GetMapping(value = "{shortUrl}")
    public ResponseEntity<String> redirect(@PathVariable String shortUrl) {
        
        // retrieve long url mapped to short url
        try {
            String url = urlService.getLongURL(shortUrl); 
          
            // create response to redirect to
            return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();

        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
}