package practice.imagine.model
import io.micronaut.core.annotation.Introspected
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@Introspected
@DynamoDbBean
data class Receiver (
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute(value = "name")
    var name: String? = null,

    @get:DynamoDbAttribute(value = "sortOrder")
    var sortOrder: Int? = 0
    )