package practice.imagine.service

import jakarta.inject.Singleton
import practice.imagine.exception.DestinationNotFoundException
import practice.imagine.model.Destination
import practice.imagine.repository.DestinationRepository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Singleton
class DestinationService(private val destinationRepository: DestinationRepository) {
    fun getDestinationByName(name: String): Mono<Destination> {
        return destinationRepository
            .findByName(name)
            .switchIfEmpty(Mono.error(DestinationNotFoundException("No destination with $name found")))
    }

    fun saveDestination(destination: Destination, create: Boolean): Mono<Destination> {
        return destinationRepository.findByName(destination.name!!)
            .flatMap<Destination> {
                IllegalArgumentException("duplicate destination, name: ${destination.name}").toMono()
            }.switchIfEmpty (
                destinationRepository.save(destination)
            )
    }

    fun getAllItems(): Mono<List<Destination>> {
        return destinationRepository.getAll().map { it.items() }
    }
}
