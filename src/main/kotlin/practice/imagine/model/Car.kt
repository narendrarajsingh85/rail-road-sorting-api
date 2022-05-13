package practice.imagine.model

import io.micronaut.core.annotation.Introspected
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@Introspected
@DynamoDbBean

data class Car(
        @get:DynamoDbPartitionKey

        @get:DynamoDbAttribute(value = "car_id")
        var carId:Int ?= 0,

        @get:DynamoDbAttribute(value = "name")
         var name:String? = null,

  /*     @get:DynamoDbAttribute(value = "model")
         var model:List<CarModels>?= null*/


)

/*data class CarModels (
        val modelName: String
)*/
