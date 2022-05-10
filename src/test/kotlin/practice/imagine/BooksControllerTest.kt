package practice.imagine

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import practice.imagine.controller.Input

@MicronautTest
class BooksControllerTest {

    @Inject
    @field:Client("/")
    lateinit var client : HttpClient

    @Test
    fun getBook() {
        val request: HttpRequest<Any> = HttpRequest.GET("/books/1234")
        val response: HttpResponse<Any> = client.toBlocking().exchange(request)
        println(response.status)
        assertEquals(HttpStatus.NOT_FOUND, response.status)
    }

    @Test
    fun hello() {
        val request: HttpRequest<Any> = HttpRequest.GET("/books/hello")
        val response: HttpResponse<Any> = client.toBlocking().exchange(request)
        assertEquals(HttpStatus.OK, response.status)
    }
}