package breed.tech.service.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constants {

    public static final Object CLOUD_WATCH_WRITER_LOCK = new Object();

    public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final String ALL_ORIGINS = "*";

    // in prod environment move ids and secrets out of code and read from env vars
    public static final String INTUIT_CLIENT_ID = "<intuit-client-id";
    public static final String INTUIT_CLIENT_SECRET = "<intuit-client-secret";

    public static final String LOG_STREAM = "<aws-log-stream>";
    public static final String LOG_GROUP = "<aws-log-group";

    public static final String TWILIO_ACCOUNT_SID = "<twilio-account-sid>";
    public static final String TWILIO_AUTH_TOKEN = "<twilio-auth-token>";

    public static final String SOURCE_TWILIO_PHONE_NUMBER = "<twilio-source-phone-number>";
    public static final Long AUTH_TOKEN_TIMEOUT_SECONDS = 60L * 60L * 24 * 7;

}
