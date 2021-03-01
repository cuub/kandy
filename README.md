# Kandy
[![](https://jitpack.io/v/cuub/kandy.svg)](https://jitpack.io/#cuub/kandy)

Kandy is a set of useful extension functions for Android.
It all started as a base library that I use in my own projects.
Some methods were at some point copied from _the internet_. If you're the owner and want credits, please let me know and thanks for contributing.


# Examples

Below you can check some of the functions available:

## Activity
```kotlin
inline fun <reified T : Activity> Activity.startActivity(options: Bundle? = null) {
    val intent = Intent()
    intent.setClass(this, T::class.java)
    startActivity(intent, options)
}
```

## Bitmap
```kotlin
fun Bitmap.getByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}
```

## Context
```kotlin
fun Context.displayWidth(): Int {
    return resources.displayMetrics.widthPixels
}
```
```kotlin
fun Context.getResourcesForApplication(packageName: String): Resources? {
    with(packageManager) {
        return try {
            getResourcesForApplication(
                getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            )
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}
```

## Drawable
```kotlin
fun Drawable.tinted(
    @ColorInt tintColor: Int? = null,
    tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
) =
    apply {
        setTintList(tintColor?.toColorStateList())
        setTintMode(tintMode)
    }
```

## Extras
```kotlin
inline fun <reified T: Any> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}
```
```kotlin
inline fun <reified T: Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}
```

## CharSequence
```kotlin
fun CharSequence?.getFileExtension(): String {
    return if (isNullOrEmpty()) {
        ""
    } else {
        this!!.substring(this.lastIndexOf("."))
    }
}
```

## Int
```kotlin
fun Int.toColor(context: Context) = ContextCompat.getColor(context, this)
```

## Koin
```kotlin
inline fun <reified T : ViewModel> ViewGroup.viewModel(): ReadOnlyProperty<ViewGroup, T> =
    object : ReadOnlyProperty<ViewGroup, T> {

        private var viewModel: T? = null

        override operator fun getValue(thisRef: ViewGroup, property: KProperty<*>): T =
            viewModel ?: getViewModel(thisRef).also { viewModel = it }

        private fun getViewModel(thisRef: ViewGroup): T {
            return (thisRef.context as FragmentActivity).getViewModel()
        }
    }
```

## Let
```kotlin
inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}
```

## LiveData
```kotlin
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
```
```kotlin
fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this) { it?.let { mediator.value = it } }
    return mediator
}
```

## View
```kotlin
fun View.setProtectedClickListener(onProtectedClick: (View) -> Unit) {
    setOnClickListener(ProtectedClickListener { v ->
        onProtectedClick(v)
    })
}
```
```kotlin
fun View.fadeAnimation(alpha: Float, duration: Long = 200L, onAnimationEnd: (() -> Unit)? = null) {
    animate()
        .alpha(alpha)
        .setDuration(duration)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                onAnimationEnd?.invoke()
            }
        })
}
```
```kotlin
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}
```

# Releases

The latest release is available on [Jitpack](https://jitpack.io/#cuub/kandy).

Step 1. Add it in your root build.gradle
```kotlin
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

```

Step 2. Add the dependency
```kotlin
dependencies {
	implementation 'com.github.cuub:kandy:Tag'
}

```


# Contribute

Feel free to create a PR with your most loved extension functions. Make it your own library too.

# License

```

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```