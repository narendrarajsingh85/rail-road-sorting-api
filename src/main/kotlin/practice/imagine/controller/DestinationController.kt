package practice.imagine.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import practice.imagine.model.Destination
import practice.imagine.service.DestinationService
import reactor.core.publisher.Mono
import javax.validation.Valid

@Validated

@Controller("/destination")
class DestinationController (
    private val destinationService: DestinationService,
) {
    @Get
    fun getAllDestinations(): Mono<HttpResponse<List<Destination>>> {
        return destinationService.getAllItems().map { HttpResponse.ok(it) }
    }

    @Get("/{name}")
    fun getDestinationByName(@PathVariable name: String): Mono<HttpResponse<Destination>> {
        return destinationService.getDestinationByName(name).map { HttpResponse.ok(it) }
    }

   @Post
    fun create(
        @Body @Valid destination: Destination
    ): Mono<HttpResponse<Destination>> {
        return destinationService
            .saveDestination(destination, true)
            .map { HttpResponse.created(it) }
    }

    @Delete("/{name}")
      fun deleteByName(@PathVariable name:String):Mono<HttpResponse<Destination>>{
                 return destinationService.delete(name).map { HttpResponse.ok(it) }
      }

    @Put("/{name}")
       fun update(@Body @Valid destination: Destination):Mono<HttpResponse<Destination>>{
           return destinationService.update(destination).map { HttpResponse.ok(it) }
       }

}