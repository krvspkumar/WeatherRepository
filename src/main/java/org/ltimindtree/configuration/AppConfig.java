package org.ltimindtree.configuration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class AppConfig {

	@Value("${aws-endpoint}")
	private String awsDynamoDBEndPoint;

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public DynamoDbClient dynamoDbClient() {
		AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
				.withCredentials(new InstanceProfileCredentialsProvider(false));

		if (!awsDynamoDBEndPoint.isEmpty()) {
			builder.withEndpointConfiguration(
					new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndPoint, "us-east-1"));
		}

		return DynamoDbClient.builder().endpointOverride(URI.create(awsDynamoDBEndPoint)).region(Region.US_EAST_1)
				.build();
	}
}
