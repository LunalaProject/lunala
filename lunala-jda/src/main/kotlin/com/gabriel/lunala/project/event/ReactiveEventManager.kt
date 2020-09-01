package com.gabriel.lunala.project.event

import com.gabriel.lunala.project.LunalaJDA
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.ExceptionEvent
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ShutdownEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.IEventManager
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import reactor.core.Disposable
import reactor.core.publisher.*
import reactor.core.scheduler.Schedulers
import reactor.util.Loggers
import java.util.logging.Level

/*
 * Thanks @MinnDevelopment!
 * @author https://github.com/MinnDevelopment/jda-reactor
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class ReactiveEventManager(private val scope: CoroutineScope): IEventManager, KoinComponent {

    private val listeners = mutableMapOf<ListenerAdapter, Disposable>()

    private val processor: FluxProcessor<GenericEvent, in GenericEvent> = Processors.multicast()
    private val scheduler = Schedulers.newSingle("Lunala EventManager", true)

    private val sink = Sinks.multicast<GenericEvent>()

    override fun handle(event: GenericEvent): Unit = scope.launch {
        try {
            sink.next(event)
        } catch (throwable: Throwable) {
            sink.next(ExceptionEvent(event.jda, throwable, true))
        }
        if (event is ShutdownEvent) {
            sink.complete()
            scheduler.dispose()
        }
    }.run { Unit }

    override fun register(listener: Any) {
        require(listener is EventListener)

        on(GenericEvent::class.java).subscribe(listener::onEvent)
    }

    fun <T : GenericEvent> on(event: Class<T>): Flux<T> {
        return processor.publishOn(scheduler)
                .log(Loggers.getLogger(LunalaJDA::class.java), Level.FINEST, true)
                .ofType(event)
    }

    override fun getRegisteredListeners(): MutableList<Any> =
            listeners.keys.toMutableList()

    override fun unregister(listener: Any) {
        require(listener is EventListener)

        val disposable = listeners.remove(listener)
        disposable?.dispose()
    }


}