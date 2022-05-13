package practice.imagine.service

import com.example.model.CarObj
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import jakarta.inject.Singleton
import practice.imagine.exception.CarNotFoundException
import practice.imagine.model.Car
import practice.imagine.model.Destination
import practice.imagine.model.Receiver
import practice.imagine.repository.CarRepository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Singleton
class CarService(private val carRepository: CarRepository) {

    @Client("http://localhost:8081")
    @Inject
    lateinit var client: HttpClient

   /* fun getCarsByTrainId(id: Int) {
        return carRepository.findByName(name)
                .switchIfEmpty(Mono.error(CarNotFoundException("No car with $name found")))
    }*/

    fun getAllCars():Mono<List<Car>>{
        return carRepository.getAllCars().map { it.items() }
    }

    fun getCarByName(name: String): Mono<Car> {
        return carRepository
                .findByName(name)
                .switchIfEmpty(Mono.error(CarNotFoundException("user not found with $name")))
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

  /*  fun saveCar(carModel:Car,create:Boolean):Mono<Car>{
        return carRepository.findByName(carModel.name!!)
                .flatMap<Car> {
                    IllegalArgumentException("Duplicate car, name: ${carModel.name}").toMono()
                }.switchIfEmpty (
                        carRepository.saveCar(carModel)
                )
    }*/

    fun create(carObj: CarObj, create:Boolean):Mono<Car>{
        var car=Car()
        car.name=carObj.name
        car.destination=getDestinationByName(carObj.destination)
        car.receiver=getReceiverByName(carObj.receiver)
        return carRepository.findByName(carObj.name!!)
                .flatMap<Car> {
                    IllegalArgumentException("duplicate receiver").toMono()}
                .switchIfEmpty(
                        carRepository.saveCar(car)
                )
    }

    private fun getReceiverByName(name: String?): Receiver? {
        val request: HttpRequest<Destination> = HttpRequest.GET("http://localhost:8081/receiver/$name")
        val rsp = client.toBlocking().exchange(request,
                Argument.of(Receiver::class.java))

        return rsp.body()
    }

    fun getDestinationByName(name: String?): Destination {
        val request: HttpRequest<Destination> = HttpRequest.GET("http://localhost:8081/destination/$name")
        val rsp = client.toBlocking().exchange(request,
                Argument.of(Destination::class.java))

        return rsp.body()

    }

    fun delete(name: String): Mono<Car> {
        return carRepository.findByName(name!!)
                .flatMap<Car> {
                    carRepository.delete(name)}
                .switchIfEmpty(
                        IllegalArgumentException("car $name not found").toMono()
                )
    }


    fun update(car: Car): Mono<Car> {
        return carRepository.findByName(car.name!!)
                .flatMap<Car> {
                    carRepository.update(car)}
                .switchIfEmpty(
                        IllegalArgumentException("car not found").toMono()
                )
    }
    fun sortCars(): Mono<List<Car>> {
        var monoCars:Mono<List<Car>> = getAllCars()
        return monoCars.map{it.sortedWith(compareBy({ it.destination?.classificationOrder }, { it.receiver?.sortOrder }))
        }
    }



    fun saveBulk(carModel:ArrayList<String>){

    }

}