package com.charapadev.blendsshop.external;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class used to configure the AWS IAM to access AWS services.
 * <p>
 * The specification of s3Url is used to permit flexibility among environments,
 * because the LocalStack can be used to simulate S3 behavior and not affect the production environment.
 */

@Configuration
public class AWSClientConfiguration {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String regionName;

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    private BasicAWSCredentials getCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(s3Url, regionName);
    }

    private AWSStaticCredentialsProvider getCredentialsProvider() {
        return new AWSStaticCredentialsProvider(getCredentials());
    }

    /**
     * Create the {@link AmazonS3 Client} to interact with S3 resources.
     *
     * @return The client built.
     */
    @Bean(name = "amazonS3")
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(getEndpointConfiguration())
            .withCredentials(getCredentialsProvider())
            .build();
    }

}
