package breed.tech.service.utility;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BCryptUtility {

    public String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashedPassword;
    }

    public boolean doesPasswordMatchHash(String password, String hashedPassword) {
        boolean result = BCrypt.checkpw(password, hashedPassword);
        return result;
    }

}