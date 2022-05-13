package practice.imagine.controller

import com.example.model.CarObj
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


    @Get("/{name}")
    fun getCarByName(@PathVariable name: String): Mono<HttpResponse<Car>> {
        return carService.getCarByName(name).map { HttpResponse.ok(it) }
    }


    @Post
    fun create(
            @Body @Valid car: CarObj
    ): Mono<HttpResponse<Car>> {
        return carService
                .create(car, true)
                .map { HttpResponse.created(it) }
    }

    @Delete("/{name}")
    fun deleteByName(@PathVariable name: String): Mono<HttpResponse<Car>> {
        return carService.delete(name)
                .map { HttpResponse.ok(it) }
    }

    @Put
    fun update(@Body @Valid car: Car): Mono<HttpResponse<Car>> {
        return carService.update(car)
                .map { HttpResponse.ok(it) }
    }

    @Get("/sort")
    fun sortCars(): Mono<HttpResponse<List<Car>>> {
        return carService.sortCars().map { HttpResponse.ok(it) }
    }



}