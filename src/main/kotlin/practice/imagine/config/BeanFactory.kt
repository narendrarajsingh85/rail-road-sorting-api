package practice.imagine.config

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import java.net.URI

@Factory
class BeanFactory(@Value("\${dynamodb.endpoint:}") val dynamoDBEndpoint: String) {

    @Singleton
    fun dynamoDbAsyncClient(): DynamoDbAsyncClient {
        var dynamoDbAsyncClientBuilder = DynamoDbAsyncClient.builder()
        if(dynamoDBEndpoint.isNotEmpty()) {
            dynamoDbAsyncClientBuilder.endpointOverride(URI.create(dynamoDBEndpoint))
        }
        return dynamoDbAsyncClientBuilder.build()
    }

    @Singleton
    fun dynamoDbEnhancedAsyncClient(): DynamoDbEnhancedAsyncClient {
        return DynamoDbEnhancedAsyncClient.builder()
            .dynamoDbClient(dynamoDbAsyncClient())
            .build()
    }
}