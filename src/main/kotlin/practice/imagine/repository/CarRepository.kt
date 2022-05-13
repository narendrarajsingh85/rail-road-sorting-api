package practice.imagine.repository

import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import practice.imagine.model.Car
import practice.imagine.model.Destination
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.Page

@Singleton
class CarRepository(
        dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient,
        @Value("\${dynamodb.car-table-name}") val carTableName: String
) {
    private val tableSchema = TableSchema.fromBean(Car::class.java)
    private val table = dynamoDbEnhancedAsyncClient.table(carTableName, tableSchema)

    fun findByName(name: String): Mono<Car> {
        val key: Key = Key.builder().partitionValue(name).build()
        return table.getItem(key).toMono()
    }

    fun findById(id: Int): Mono<Car> {
        val key: Key = Key.builder().partitionValue(id).build()
        return table.getItem(key).toMono()
    }

    fun findCarsByTrainId() {

    }

    fun saveCar(car: Car): Mono<Car> {
        return table.putItem(car)
                .thenApply { car }
                .toMono()
    }

    fun getAllCars(): Mono<Page<Car>> {
        return table.scan().toMono()
    }


}