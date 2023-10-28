package breed.tech.service.manager;

import breed.tech.service.model.AuthToken;
import breed.tech.service.model.User;
import breed.tech.service.repository.AuthTokenRepository;
import breed.tech.service.repository.UserRepository;
import breed.tech.service.utility.AuthTokenGenerator;
import breed.tech.service.utility.TimeUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static breed.tech.service.constants.Constants.GSON;

@Service
@Slf4j
public class AuthTokenManager {

    public static final Integer RETRY_COUNT_MAX = 5;
    private AuthTokenGenerator authTokenGenerator;
    private AuthTokenRepository authTokenRepository;
    private TimeUtility timeUtility;
    private UserRepository userRepository;

    @Autowired
    public AuthTokenManager(
            AuthTokenGenerator authTokenGenerator,
            AuthTokenRepository authTokenRepository,
            TimeUtility timeUtility,
            UserRepository userRepository) {
        this.authTokenGenerator = Validate.notNull(authTokenGenerator);
        this.authTokenRepository = Validate.notNull(authTokenRepository);
        this.timeUtility = Validate.notNull(timeUtility);
        this.userRepository = Validate.notNull(userRepository);
    }

    public AuthToken getAuthTokenObjectFromAuthToken(String authToken) {
        AuthToken authTokenObject = this.authTokenRepository.findByAuthToken(authToken);
        return authTokenObject;
    }

    public User getUserFromAuthToken(String authToken) {
        AuthToken authTokenObject = this.authTokenRepository.findByAuthToken(authToken);
        log.info("authTokenObject: " + GSON.toJson(authTokenObject));
        String emailAddress = authTokenObject.getEmailAddress();
        log.info("emailAddress: " + emailAddress);
        User user = this.userRepository.findByEmailAddressIgnoreCase(emailAddress);
        return user;
    }

    public AuthToken generateUniqueAuthToken(String emailAddress, String accountType) {
        AuthToken uniqueAuthToken = null;
        for (int i=0;i<RETRY_COUNT_MAX;i++) {
            String authTokenString = authTokenGenerator.generateRandomAuthToken();
            Long validUntilUnixTime = this.timeUtility.getUnixTimeoutTimestampForAuthToken();
            validUntilUnixTime = validUntilUnixTime + (200 * 60 * 60 * 24);
            AuthToken authToken = AuthToken.builder()
                    .emailAddress(emailAddress)
                    .authToken(authTokenString)
                    .validUntilUnixTime(validUntilUnixTime)
                    .accountType(accountType)
                    .build();
            try {
                uniqueAuthToken = this.authTokenRepository.save(authToken);
                log.info("UniqueAuthToken generated = " + GSON.toJson(authToken));
                log.info("Return value from auth token save: " + GSON.toJson(uniqueAuthToken));
                break;
            } catch (Exception e) {
                e.printStackTrace();
                log.info("failed to generate a unique auth token");
                System.out.println("failed to generate a unique auth token");
            }
        }
        return uniqueAuthToken;
    }

}