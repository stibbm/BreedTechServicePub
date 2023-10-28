package breed.tech.service.beans;

import breed.tech.service.manager.SecretsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Configuration
public class SecretsManagerProvider {

    @Bean
    public SecretsManager provideSecretsManager() {
        SecretsManagerClient secretsManagerClient = SecretsManagerClient.create();
        SecretsManager secretsManager = new SecretsManager(secretsManagerClient);
        return secretsManager;
    }
}