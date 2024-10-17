package org.ltimindtree.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class AppConfig {

	@Value("${aws.dynamodb.accesskey}")
	private String awsAccessKey;

	@Value("${aws.dynamodb.secretkey}")
	private String awsSecretKey;

	@Value("${aws.dynamodb.endpoint}")
	private String awsDynamoDBEndPoint;

	@Value("${aws.dynamodb.region}")
	private String awsRegion;

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

//	@Bean
//	public DynamoDbClient dynamoDbClient() {
//		return DynamoDbClient.create();
//	}

	@Bean
	public DynamoDbClient dynamoDbClient() {
		return DynamoDbClient.builder().endpointOverride(URI.create(awsDynamoDBEndPoint)).region(Region.US_EAST_1)
				.credentialsProvider(
						StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
				.build();
	}
}
