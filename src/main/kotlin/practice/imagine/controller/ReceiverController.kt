package practice.imagine.controller

import practice.imagine.model.Receiver
import practice.imagine.service.ReceiverService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import javax.validation.Valid
@Validated
@Controller("/receiver")
class ReceiverController(val receiverService: ReceiverService) {

    @Get
    fun getAllReceivers(): Mono<HttpResponse<List<Receiver>>> {
        return receiverService.getAllItems().map { HttpResponse.ok(it) }
    }

    @Get("/test")
    fun getArray():Flux<Int>{
        return arrayOf(1,2,3,4).toFlux()
    }

    @Get("/{name}")
    fun getReceiver(@PathVariable name:String):Mono<HttpResponse<Receiver>>{
        return receiverService.getReceiverByName(name).map{HttpResponse.ok(it)}
    }

    @Post
    fun save(@Body @Valid receiver: Receiver):Mono<HttpResponse<Receiver>>{
        return receiverService.create(receiver,true)
            .map { HttpResponse.created((it)) }
    }

    @Delete("/{name}")
    fun deleteByName(@PathVariable name:String):Mono<HttpResponse<Receiver>>{
        return receiverService.delete(name)
            .map{HttpResponse.ok(it)}
    }

    @Put
    fun update(@Body @Valid receiver: Receiver):Mono<HttpResponse<Receiver>>{
        return receiverService.update(receiver)
            .map{HttpResponse.ok(it)}
    }
}