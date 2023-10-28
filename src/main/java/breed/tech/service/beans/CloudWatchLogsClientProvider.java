package breed.tech.service.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;

@Configuration
public class CloudWatchLogsClientProvider {

    @Bean
    public CloudWatchLogsClient provideCloudWatchLogsClient() {
        CloudWatchLogsClient cloudWatchLogsClient = CloudWatchLogsClient.create();
        return cloudWatchLogsClient;
    }
}