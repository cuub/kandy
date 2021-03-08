package pt.hugofernandes.kandy.ktx

import android.animation.Animator
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pt.hugofernandes.kandy.ProtectedClickListener

/**
 * Sets a custom click listener that ignores any click within the given [interval]
 * @param interval The interval used to ignore any clicks. Defaults to 250L
 * @param onProtectedClick What to be invoked
 *
 * Usage example:
 * view.setProtectedClickListener { }
 */
fun View.setProtectedClickListener(interval: Long = 250L, onProtectedClick: (View) -> Unit) {
    setOnClickListener(ProtectedClickListener(interval) { v ->
        onProtectedClick(v)
    })
}

/**
 * Fades the view to the give [alpha] with the given [duration]
 * @param alpha The final alpha value wanted to ve set
 * @param duration The duration for the underlying animator that animates the requested properties. Defaults to 200L
 * @param onAnimationEnd To be invoked when the animation ends. It's optional
 *
 * Usage example:
 * view.fadeAnimation(0.6f)
 */
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

/**
 * Invokes [f] after view [T] is measured
 *
 * Usage example:
 * view.afterMeasured { //do something after the view has been measured }
 */
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

fun <F : Fragment> View.findFragment() =
    runCatching { FragmentManager.findFragment(this) as F }.getOrNull()