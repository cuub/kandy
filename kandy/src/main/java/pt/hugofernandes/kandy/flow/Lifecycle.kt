package pt.hugofernandes.kandy.flow

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

/**
 * Accepts the [collector] on the provided [flow] and emits values into it in a lifecycle-aware
 * manner. The collect operation is launched using the [owner]'s [LifecycleCoroutineScope] and
 * automatically repeated on lifecycle event [Lifecycle.State.STARTED].
 * @param owner The [LifecycleOwner] which the lifecycle-aware [Flow.collect] operation is tied to.
 * @param flow The data stream from which values will be emitted.
 * @param coroutineDispatcher The [CoroutineDispatcher] used to launch the collect operations.
 * @param collector The consumer of emitted data.
 */
@PublishedApi
internal inline fun <T> lifecycleAwareCollect(
    owner: LifecycleOwner,
    flow: Flow<T>,
    coroutineDispatcher: CoroutineDispatcher,
    crossinline collector: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch(coroutineDispatcher) {
    owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        flow.collect { collector(it) }
    }
}

/**
 * Creates a lifecycle-aware [CoroutineScope] appropriate for launching [Flow.collect] operations.
 * The collect operations launched in this scope will be automatically repeated on lifecycle event
 * [Lifecycle.State.STARTED].
 * @param owner The [LifecycleOwner] which the lifecycle-aware [Flow.collect] operation is tied to.
 * @param coroutineDispatcher The [CoroutineDispatcher] used to launch the collect operations.
 * @param block A block which receives a [CoroutineScope] used to launch [Flow.collect] operations.
 */
@PublishedApi
internal inline fun lifecycleAwareCollectorScope(
    owner: LifecycleOwner,
    coroutineDispatcher: CoroutineDispatcher,
    crossinline block: suspend CoroutineScope.() -> Unit
) = owner.lifecycleScope.launch(coroutineDispatcher) {
    owner.repeatOnLifecycle(Lifecycle.State.STARTED) { block() }
}
