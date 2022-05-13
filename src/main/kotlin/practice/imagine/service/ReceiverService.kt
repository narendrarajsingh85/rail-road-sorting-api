package practice.imagine.service

import practice.imagine.model.Receiver
import practice.imagine.repository.ReceiverRepository
import jakarta.inject.Singleton
import practice.imagine.exception.ReceiverNotFoundException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Singleton
class ReceiverService(private val receiverRepository: ReceiverRepository) {

    fun getAllItems(): Mono<List<Receiver>> {
        return receiverRepository.getAll().map { it.items() }
    }

    fun getReceiverByName(name: String): Mono<Receiver> {
        return receiverRepository
            .findByName(name)
            .switchIfEmpty(Mono.error(ReceiverNotFoundException("Receiver not found with $name")))
    }

    fun create(receiver: Receiver, create:Boolean):Mono<Receiver>{
        return receiverRepository.findByName(receiver.name!!)
            .flatMap<Receiver> {
                IllegalArgumentException("duplicate receiver").toMono()}
                    .switchIfEmpty(
                        receiverRepository.save(receiver)
                    )
            }

    fun delete(name: String): Mono<Receiver> {
        return receiverRepository.findByName(name)
            .flatMap {
                receiverRepository.delete(name)}
            .switchIfEmpty(
                IllegalArgumentException("city $name not found").toMono()
            )
    }

    fun update(receiver: Receiver): Mono<Receiver> {
        return receiverRepository.findByName(receiver.name!!)
            .flatMap<Receiver> {
                receiverRepository.update(receiver)}
            .switchIfEmpty(
                IllegalArgumentException("city not found").toMono()
            )
    }
    }



