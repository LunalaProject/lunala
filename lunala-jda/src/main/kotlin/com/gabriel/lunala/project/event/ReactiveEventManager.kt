package com.gabriel.lunala.project.event

import com.gabriel.lunala.project.utils.events.EventHolder
import com.gabriel.lunala.project.utils.events.LunalaEventManager
import com.gabriel.lunala.project.utils.events.toListener
    import net.dv8tion.jda.api.events.ExceptionEvent
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.IEventManager
import org.koin.core.KoinComponent
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxProcessor
import reactor.core.publisher.Processors
import reactor.core.publisher.SequenceSink
import reactor.core.scheduler.Schedulers
import java.security.InvalidParameterException

/*
 * Thanks @MinnDevelopment!
 * @author https://github.com/MinnDevelopment/jda-reactor
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class ReactiveEventManager: IEventManager, LunalaEventManager, KoinComponent {

    private val listeners = mutableMapOf<EventListener, Disposable>()

    private val processor: FluxProcessor<GenericEvent, in GenericEvent> = Processors.multicast()
    private val scheduler = Schedulers.newSingle("Lunala EventManager", true)

    private val sink: SequenceSink<GenericEvent> = processor.sink()

    override fun register(listener: Any) = try {
        listeners[validateListener(listener)] = on(GenericEvent::class.java).subscribe(validateListener(listener)::onEvent)
    } catch (exception: InvalidParameterException) {}

    override fun handle(event: GenericEvent) {
        kotlin.runCatching {
            sink.next(event)
        }.exceptionOrNull()?.let {
            sink.next(ExceptionEvent(event.jda, it, false))
        }
    }

    override fun getRegisteredListeners(): MutableList<Any> =
            listeners.keys.toMutableList()

    fun <T : GenericEvent> on(event: Class<T>): Flux<T> =
        processor.publishOn(scheduler).ofType(event)

    override fun unregister(listener: Any) = try {
        listeners.remove(validateListener(listener))?.dispose().run { Unit }
    } catch (exception: InvalidParameterException) {}

    private fun validateListener(listener: Any) = when (listener) {
        is EventListener -> listener
        is EventHolder -> listener.toListener()
        else -> throw InvalidParameterException()
    }

}