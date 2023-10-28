package breed.tech.service.beans;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Provider {
    @Bean
    public AmazonS3 getAmazonS3Bean() {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();
        return amazonS3;
    }
}