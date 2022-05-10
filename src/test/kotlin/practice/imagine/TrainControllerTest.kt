package practice.imagine

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Test
import practice.imagine.controller.Input

@MicronautTest
class TrainControllerTest {

    @Inject
    @field:Client("/")
    lateinit var client : HttpClient

    @Test
    fun testSort() {
        val input = listOf<Input>(
                Input(nameOfCar = "Box Car 1", destination = "Houston", receiver = "FedEx"),
                Input(nameOfCar = "Box Car 2", destination = "Chicago", receiver = "FedEx"),
                Input(nameOfCar = "Box Car 3", destination = "Houston", receiver = "UPS"),
                Input(nameOfCar = "Box Car 4", destination = "LA", receiver = "Old Dominion")
        )
        val request: HttpRequest<Any> = HttpRequest.POST("/trains", input)
        val response: HttpResponse<Any> = client.toBlocking().exchange(request)
        assertEquals(HttpStatus.OK, response.status)
    }
}