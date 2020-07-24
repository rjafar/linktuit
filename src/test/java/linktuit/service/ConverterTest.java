package linktuit.linktuit.service;

// // internal classes
import linktuit.linktuit.service.Converter;

// Junit test packages
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ConverterTest {
    private Converter converter = new Converter();

    @Test
    public void base10ToBase62Test() {
        assertEquals("a", converter.base10ToBase62((long)0));
        assertEquals("g8", converter.base10ToBase62((long)432));
        assertEquals("999", converter.base10ToBase62((long)238327));
    }

    @Test
    public void base62ToBase10Test() {
        assertEquals((long)0, converter.base62ToBase10("a"));
        assertEquals((long)432, converter.base62ToBase10("g8"));
        assertEquals((long)238327, converter.base62ToBase10("999"));
    }


}