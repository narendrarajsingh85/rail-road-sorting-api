package practice.imagine.repository

import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import practice.imagine.model.Destination
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@Singleton
class DestinationRepository(
    dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient,
    @Value("\${dynamodb.destination-table-name}") val destinationTableName: String
) {
    private val tableSchema = TableSchema.fromBean(Destination::class.java)
    private val table = dynamoDbEnhancedAsyncClient.table(destinationTableName, tableSchema)

    fun findByName(name: String): Mono<Destination> {
        val key: Key = Key.builder().partitionValue(name).build()
        return table.getItem(key).toMono()
    }

    fun save(destination: Destination): Mono<Destination> {
        return table.putItem(destination)
            .thenApply { destination }
            .toMono()
    }

    fun getAll(): Mono<Page<Destination>> {
        return table.scan().toMono()
    }
}