package breed.tech.service.manager;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static breed.tech.service.constants.Constants.GSON;

@Service
@Slf4j
public class S3FileManager {

    private AmazonS3 amazonS3;

    @Autowired
    public S3FileManager(AmazonS3 amazonS3) {
        this.amazonS3 = Validate.notNull(amazonS3);
    }

    public byte[] getS3File(String s3BucketName, String s3FilePath) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(s3BucketName, s3FilePath);
        S3Object s3Object = amazonS3.getObject(getObjectRequest);
        try {
            byte[] bytes = IOUtils.toByteArray(s3Object.getObjectContent());
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createFile(byte[] fileBytes, String s3BucketName, String s3FilePath) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileBytes.length);
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(
                        s3BucketName, s3FilePath, byteArrayInputStream, objectMetadata);
        PutObjectResult putObjectResult = this.amazonS3.putObject(putObjectRequest);
        log.info("putObjectResult: " + GSON.toJson(putObjectResult));
    }

    public boolean doesFileExist(String s3BucketName, String s3FilePath) {
        try {
            amazonS3.getObjectMetadata(s3BucketName, s3FilePath);
        } catch (AmazonServiceException amazonServiceException) {
            return false;
        }
        return true;
    }
}