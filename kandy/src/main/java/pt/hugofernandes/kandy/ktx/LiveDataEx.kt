package pt.hugofernandes.kandy.ktx

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

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