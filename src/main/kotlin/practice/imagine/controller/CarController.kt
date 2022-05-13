package practice.imagine.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import practice.imagine.model.Car
import practice.imagine.model.Destination
import practice.imagine.service.CarService
import reactor.core.publisher.Mono
import javax.validation.Valid


@Validated

@Controller("/car")
class CarController(private val carService: CarService) {

  @Get
    fun getAllCars():Mono<HttpResponse<List<Car>>>{
        return carService.getAllCars().map { HttpResponse.ok(it) }
    }

    @Get("/{id}/{name}")
      fun getCarByIdAndName(@PathVariable id:Int,name:String):Mono<HttpResponse<Car>>{
         return carService.getCarByIdAndName(id,name).map { HttpResponse.ok(it) }
      }


     /*  @Get("/{id}")
        fun getCarByTrainID(@PathVariable id:Int):Mono<HttpResponse<Car>>{
            return carService.getCarsByTrainId(id).map { HttpResponse.ok(it) }
        }*/


    @Post
    fun create(
            @Body @Valid car: Car
    ): Mono<HttpResponse<Car>> {
        return carService
                .saveCar(car, true)
                .map { HttpResponse.created(it) }
    }



}