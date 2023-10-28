package breed.tech.service.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;

@Configuration
public class CloudWatchClientProvider {

    @Bean
    public CloudWatchClient provideCloudWatchClient() {
        CloudWatchClient cloudWatchClient = CloudWatchClient.create();
        return cloudWatchClient;
    }
}