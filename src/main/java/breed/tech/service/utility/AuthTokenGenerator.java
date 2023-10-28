package breed.tech.service.utility;

import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthTokenGenerator {

    public static final Integer SIZE_AUTH_TOKEN = 32;
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();
    public static final Base64.Encoder BASE_64_ENCODER = Base64.getUrlEncoder();

    public String generateRandomAuthToken() {
        byte[] bytes = new byte[SIZE_AUTH_TOKEN];
        SECURE_RANDOM.nextBytes(bytes);
        String randomString = BASE_64_ENCODER.encodeToString(bytes);
        return randomString;
    }

    public Long getUnixTimestampFromJodaDateTime(DateTime dateTime) {
        Long unixTimestamp = dateTime.getMillis() / 1000;
        return unixTimestamp;
    }

    public String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashedPassword;
    }

}