package com.hobby.chain.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectStorageConfig {
    @Value("${naver.objectStorage.endpoint}")
    private String endPoint;
    @Value("${naver.objectStorage.regionName}")
    private String regionName;
    @Value("${naver.objectStorage.accessKey}")
    private String accessKey;
    @Value("${naver.objectStorage.secretKey}")
    private String secretKey;

    @Bean
    public BasicAWSCredentials AwsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 awsS3Client() {

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withChunkedEncodingDisabled(true)
                .build();
    }

}
