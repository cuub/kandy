package pt.hugofernandes.kandy.flow

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

/** A named reference to this [LifecycleOwner]'s instance. */
@PublishedApi
internal inline val LifecycleOwner.lifecycleOwner: LifecycleOwner
    get() = this

/**
 * Accepts the [collector] on this [Flow] and emits values into it in a lifecycle-aware
 * manner. The collect operation is launched using the [owner]'s [LifecycleCoroutineScope] and
 * automatically repeated on lifecycle event [Lifecycle.State.STARTED].
 * @param owner The [LifecycleOwner] which the lifecycle-aware [Flow.collect] operation is tied to.
 * @param coroutineDispatcher The [CoroutineDispatcher] used to launch the collect operation.
 * @param collector The consumer of emitted data.
 */
@PublishedApi
internal inline fun <T> Flow<T>.lifecycleAwareCollect(
    owner: LifecycleOwner,
    coroutineDispatcher: CoroutineDispatcher,
    crossinline collector: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch(coroutineDispatcher) {
    owner.repeatOnLifecycle(Lifecycle.State.STARTED) { collect { collector(it) } }
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
