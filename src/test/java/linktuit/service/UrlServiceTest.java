package linktuit.linktuit.service;

// internal classes
import linktuit.linktuit.dto.UrlDTO;
import linktuit.linktuit.modal.UrlMapping;
import linktuit.linktuit.service.Converter;
import linktuit.linktuit.service.UrlServiceImpl;
import linktuit.linktuit.dao.UrlDAO;

// Junit test packages
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Java utility packages
import java.util.Date;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {
    // create mock instances
    @Mock
    UrlDAO mockDao;

    @Mock
    Converter mockConverter;

    // inject mock instances as dependencies
    @InjectMocks
    UrlServiceImpl urlService;

    @Test
    public void shortenURLTest() {
        UrlMapping url = new UrlMapping();
        url.setOriginalURL("https://revanjafar.com");
        url.setCreatedDate(new Date());
        url.setNumRequestsLeft(10);
        url.setId((long)5);

        when(mockDao.save(any(UrlMapping.class))).thenReturn((long)5);
        when(mockConverter.base10ToBase62(url.getId())).thenReturn("f");

        UrlDTO urlRequest = new UrlDTO();
        urlRequest.setOriginalUrl("https://revanjafar.com");

        assertEquals("f", urlService.shortenURL(urlRequest));
    }

    @Test
    public void getLongURLTest() {
        when(mockConverter.base62ToBase10("h")).thenReturn((long) 7);

        UrlMapping url = new UrlMapping();
        url.setOriginalURL("https://revanjafar.com");
        url.setCreatedDate(new Date());
        url.setNumRequestsLeft(10);
        url.setId(7);

        when(mockDao.findMapping((long) 7)).thenReturn("https://revanjafar.com");
        assertEquals("https://revanjafar.com", urlService.getLongURL("h"));
    
    }
}