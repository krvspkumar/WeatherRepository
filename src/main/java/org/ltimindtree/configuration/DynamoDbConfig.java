//package org.ltimindtree.configuration;
//
//import java.net.URI;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//
//import okhttp3.OkHttpClient;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
//
//@Configuration
//public class DynamoDbConfig {
//
//
//    @Value("${aws.dynamodb.accesskey}")
//    private String awsAccessKey;
//
//    @Value("${aws.dynamodb.secretkey}")
//    private String awsSecretKey;
//
//    @Value("${aws.dynamodb.endpoint}")
//    private String awsDynamoDBEndPoint;
//
//    @Value("${aws.dynamodb.region}")
//    private String awsRegion;
//
//    @Bean
//    public AWSCredentials amazonAWSCredentials(){
//        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
//    }
//
//    @Bean
//    public OkHttpClient okHttpClient() {
//        return new OkHttpClient();
//    }
//
//    @Bean
//    public AWSCredentialsProvider amazonAWSCredentialsProvider(){
//        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
//    }
//
//    @Bean
//    public AmazonDynamoDB amazonDynamoDB(){
//        return AmazonDynamoDBClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndPoint, awsRegion))
//                .withCredentials(amazonAWSCredentialsProvider())
//                .build();
//    }
//
//    @Bean
//    public DynamoDBMapper mapper(){
//        return new DynamoDBMapper(amazonDynamoDB());
//    }
//    
//    @Bean
//    public DynamoDbClient getDynamoDbClient() {
//      return DynamoDbClient.builder()
//          .endpointOverride(URI.create(awsDynamoDBEndPoint))
//          .region(Region.US_EAST_1)
//          .credentialsProvider(StaticCredentialsProvider.create(
//              AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
//          .build();
//    }
//    
//    @Bean
//    public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
//      return DynamoDbEnhancedClient.builder()
//          .dynamoDbClient(getDynamoDbClient())
//          .build();
//    }
//}
