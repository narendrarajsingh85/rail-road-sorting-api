package practice.imagine.repository

import practice.imagine.model.Receiver
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.model.Page
@Singleton
class ReceiverRepository(dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient,
@Value("\${dynamodb.receiver-table-name}") val receiverTable:String) {
    private val schema=TableSchema.fromBean(Receiver::class.java)
    private val table=dynamoDbEnhancedAsyncClient.table(receiverTable,schema)

    fun getAll(): Mono<Page<Receiver>> {
        return table.scan().toMono()
    }

    fun findByName(name:String):Mono<Receiver>{
        val key:Key= Key.builder().partitionValue(name).build()
        return table.getItem(key).toMono()
    }

    fun save(receiver: Receiver):Mono<Receiver>{
        return table.putItem(receiver)
            .thenApply{receiver}.toMono()
    }

    fun delete(name:String):Mono<Receiver>{
        val key:Key= Key.builder().partitionValue(name).build()
        return table.deleteItem(key).toMono()
    }

    fun update(receiver: Receiver): Mono<Receiver> {
        return table.updateItem(receiver).thenApply { receiver }.toMono()

    }


}