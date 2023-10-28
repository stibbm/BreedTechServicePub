package breed.tech.service.utility;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class RandomTokenGenerator {

    public static final Integer DEFAULT_SIZE = 32;
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();
    public static final Base64.Encoder BASE_64_ENCODER = Base64.getUrlEncoder();

    /**
     * Generate random token of the specified size in bytes
     * @param size
     * @return
     */
    public String generateRandomToken(int size) {
        byte[] bytes = new byte[size];
        SECURE_RANDOM.nextBytes(bytes);
        String randomString = new String(bytes);
        return randomString;
    }

    /**
     * Generate random token with the default size in bytes
     * @return
     */
    public String generateRandomToken() {
        return generateRandomToken(DEFAULT_SIZE);
    }
}