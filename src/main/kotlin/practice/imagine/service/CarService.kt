package practice.imagine.service

import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import practice.imagine.exception.CarNotFoundException
import practice.imagine.model.Car
import practice.imagine.repository.CarRepository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Singleton
class CarService(private val carRepository: CarRepository) {

   /* fun getCarsByTrainId(id: Int) {
        return carRepository.findByName(name)
                .switchIfEmpty(Mono.error(CarNotFoundException("No car with $name found")))
    }*/

    fun getAllCars():Mono<List<Car>>{
        return carRepository.getAllCars().map { it.items() }
    }

     fun sortCarsByTrainId(){

     }

    fun getCarByIdAndName (id:Int,name:String):Mono<Car>{
          return carRepository.findByName(name)
                  carRepository.findById(id)
                  .switchIfEmpty(Mono.error(CarNotFoundException("No car with $name found")))
    }

     fun sortCarsByCarNames(){

     }

    fun saveCar(carModel:Car,create:Boolean):Mono<Car>{
        return carRepository.findByName(carModel.name!!)
                .flatMap<Car> {
                    IllegalArgumentException("Duplicate car, name: ${carModel.name}").toMono()
                }.switchIfEmpty (
                        carRepository.saveCar(carModel)
                )
    }



    fun saveBulk(carModel:ArrayList<String>){

    }

}