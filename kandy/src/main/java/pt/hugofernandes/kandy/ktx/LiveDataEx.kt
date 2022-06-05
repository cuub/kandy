package pt.hugofernandes.kandy.ktx

import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import pt.hugofernandes.kandy.flow.lifecycleOwner

/**
 * Debounce updates on the given [LiveData].
 *
 * @param duration Debounce duration, defaults to 250L
 *
 * Usage example:
 * liveData.debounce().observe(lifecycleOwner Observer{})
 */
fun <T> LiveData<T>.debounce(mHandler: Handler? = null, duration: Long = 250L) =
    MediatorLiveData<T>().also { mld ->
        val source = this
        val handler = mHandler ?: Handler(Looper.getMainLooper())
        val runnable = Runnable {
            mld.value = source.value
        }
        mld.addSource(source) {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, duration)
        }
    }

/**
 * Filters any null values set on the given [LiveData]
 *
 * Usage example:
 * liveData
 *   .nonNull()
 *   .distinct()
 *   .observe(lifecycleOwner, { result ->
 *      // distinct result is received here
 *   })
 */
fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this) { it?.let { mediator.value = it } }
    return mediator
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

/**
 * Only updates the given [LiveData] if the new value if different from the current one
 */
fun <T> LiveData<T>.distinct(): LiveData<T> {
    val mediatorLiveData: MediatorLiveData<T> = MediatorLiveData()
    mediatorLiveData.addSource(this) {
        if (it != mediatorLiveData.value) {
            mediatorLiveData.value = it
        }
    }
    return mediatorLiveData
}

fun <T> NonNullMediatorLiveData<T>.distinct(): LiveData<T> {
    val mediatorLiveData: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediatorLiveData.addSource(this) {
        if (it != mediatorLiveData.value) {
            mediatorLiveData.value = it
        }
    }
    return mediatorLiveData
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (t: T?) -> Unit) {
    observe(owner, Observer { observer(it) })
}

class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

/**
 * Creates a new LiveData object that does not emit a value until the this LiveData value has been
 * changed. The value is considered changed if `equals()` yields `false`.
 */
fun <T> LiveData<T>.distinctUntilChanged() =
    Transformations.distinctUntilChanged(this)

//region Lifecycle-aware observe
/**
 * Adds the given [onChange] block as this [LiveData]'s observer within the this `Activity` lifespan
 * and returns a reference to the observer.
 */
context(AppCompatActivity)
inline fun <T> LiveData<T>.observe(crossinline onChange: (T) -> Unit) =
    observe(lifecycleOwner, onChange)

/**
 * Adds the given [onChange] block as this [LiveData]'s observer within the this `Activity` lifespan
 * and returns a reference to the observer.
 * [onChange] will not be called on subsequent repetitions of this [LiveData]'s `value`.
 */
context(AppCompatActivity)
inline fun <T> LiveData<T>.onChange(crossinline onChange: (T) -> Unit) =
    distinctUntilChanged().observe(lifecycleOwner, onChange)

/**
 * Adds the given [onChange] block as this [LiveData]'s observer within the this [Fragment]'s
 * `viewLifecycleOwner` lifespan and returns a reference to the observer.
 */
context(Fragment)
inline fun <T> LiveData<T>.observe(crossinline onChange: (T) -> Unit) =
    observe(viewLifecycleOwner, onChange)

/**
 * Adds the given [onChange] block as this [LiveData]'s observer within the this [Fragment]'s
 * `viewLifecycleOwner` lifespan and returns a reference to the observer.
 * [onChange] will not be called on subsequent repetitions of this [LiveData]'s `value`.
 */
context(Fragment)
inline fun <T> LiveData<T>.onChange(crossinline onChange: (T) -> Unit) =
    distinctUntilChanged().observe(viewLifecycleOwner, onChange)

/**
 * Adds the given [onChange] block as this [LiveData]'s observer within the this `Service` lifespan
 * and returns a reference to the observer.
 */
context(LifecycleService)
inline fun <T> LiveData<T>.observe(crossinline onChange: (T) -> Unit) =
    observe(lifecycleOwner, onChange)

/**
 * Adds the given [onChange] block as this [LiveData]'s observer within the this `Service` lifespan
 * and returns a reference to the observer.
 * [onChange] will not be called on subsequent repetitions of this [LiveData]'s `value`.
 */
context(LifecycleService)
inline fun <T> LiveData<T>.onChange(crossinline onChange: (T) -> Unit) =
    distinctUntilChanged().observe(lifecycleOwner, onChange)

/**
 * Adds the given [onChange] block as this [LiveData]'s observer within the this [View]'s lifespan
 * and returns a reference to the observer.
 */
context(View, LifecycleOwner)
inline fun <T> LiveData<T>.observe(crossinline onChange: (T) -> Unit) =
    observe(lifecycleOwner, onChange)

/**
 * Adds the given [onChange] block as this [LiveData]'s observer within the this [View]'s lifespan
 * and returns a reference to the observer.
 * [onChange] will not be called on subsequent repetitions of this [LiveData]'s `value`.
 */
context(View, LifecycleOwner)
inline fun <T> LiveData<T>.onChange(crossinline onChange: (T) -> Unit) =
    distinctUntilChanged().observe(lifecycleOwner, onChange)
//endregion
