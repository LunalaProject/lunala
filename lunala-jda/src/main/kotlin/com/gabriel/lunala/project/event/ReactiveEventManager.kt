package com.gabriel.lunala.project.event

import kotlinx.coroutines.CoroutineScope
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

/*
 * Thanks @MinnDevelopment!
 * @author https://github.com/MinnDevelopment/jda-reactor
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class ReactiveEventManager(private val scope: CoroutineScope): IEventManager, KoinComponent {

    private val listeners = mutableMapOf<EventListener, Disposable>()

    private val processor: FluxProcessor<GenericEvent, in GenericEvent> = Processors.multicast()
    private val scheduler = Schedulers.newSingle("Lunala EventManager", true)

    private val sink: SequenceSink<GenericEvent> = processor.sink()

    override fun register(listener: Any) {
        listeners[(listener as? EventListener?) ?: return] = on(GenericEvent::class.java).subscribe(((listener as? EventListener) ?: return)::onEvent)
    }

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

    override fun unregister(listener: Any) =
        listeners.remove(listener as? EventListener)?.dispose().run { Unit }

}