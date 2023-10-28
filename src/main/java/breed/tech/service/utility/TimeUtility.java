package breed.tech.service.utility;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static breed.tech.service.constants.Constants.AUTH_TOKEN_TIMEOUT_SECONDS;

@Service
public class TimeUtility {
    public Long getUnixTimestampFromJodaTimestamp(DateTime dateTime) {
        Long unixTimestamp = dateTime.getMillis() / 1000;
        return unixTimestamp;
    }

    public Long getCurrentUnixTimestamp() {
        Instant now = Instant.now();
        Long currentUnixTime = now.getEpochSecond();
        return currentUnixTime;
    }

    public Long getUnixTimeoutTimestampForAuthToken() {
        Long currentTime = getCurrentUnixTimestamp();
        Long timeoutTime = currentTime + AUTH_TOKEN_TIMEOUT_SECONDS;
        return timeoutTime;
    }

}