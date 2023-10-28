package breed.tech.service.manager;

import org.apache.commons.lang3.Validate;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretResponse;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretsManager {

    private SecretsManagerClient secretsManagerClient;

    public SecretsManager(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = Validate.notNull(secretsManagerClient);
    }

    public CreateSecretResponse createSecret(String secretName, String secretValue) {
        try {
            CreateSecretRequest createSecretRequest =
                    CreateSecretRequest.builder()
                            .name(secretName)
                            .description("description")
                            .secretString(secretValue)
                            .build();
            CreateSecretResponse createSecretResponse =
                    this.secretsManagerClient.createSecret(createSecretRequest);
            return createSecretResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetSecretValueResponse getSecret(String secretId) {
        try {
            GetSecretValueRequest getSecretValueRequest =
                    GetSecretValueRequest.builder().secretId(secretId).build();
            GetSecretValueResponse getSecretValueResponse =
                    this.secretsManagerClient.getSecretValue(getSecretValueRequest);
            return getSecretValueResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}