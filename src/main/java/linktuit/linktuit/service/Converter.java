package linktuit.linktuit.service;

import org.springframework.stereotype.Service;

/**
 * This class serves as a base converter, taking the unique ID from the database and
 * converting it from base 10 to base 62. It also does the opposite process to decode 
 * back to the original ID. 
 */
@Service
public class Converter {
    private static final String base62chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] base62alphabet = base62chars.toCharArray();
    private int base = base62alphabet.length; // 62 total characters

    /**
     * Convert from base 10 to base 62 then get the string representation
     * @param base10id
     * @return base 62 represented as a string
     */
    public String base10ToBase62(long base10id) {
        StringBuilder shortURL = new StringBuilder();

        // base case when id == 0
        if (base10id == 0) {
            char ch = base62alphabet[0];
            return String.valueOf(ch);
        }
        // algorithm to convert to base 62
        while (base10id > 0) {
            int idx = (int)(base10id % base);
            shortURL.append(base62alphabet[idx]);
            base10id = base10id / base;
        }
        return shortURL.reverse().toString(); 
    }

    /**
     * Convert from the string representation of base 62 to base 10 
     * @param base62str
     * @return long base 10 ID
     */
    public long base62ToBase10(String base62str) {
        char [] inputStringArr = base62str.toCharArray();
        int length = inputStringArr.length;

        long id = 0;
        int count = 1;

        for (int i = 0; i < length; i++) {
            char c = inputStringArr[i];
            int idx = base62chars.indexOf(c);
            id += idx * (long)Math.pow(base, length - count);
            count++;
        }
        return id;
    }
}
