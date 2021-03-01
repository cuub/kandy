package pt.hugofernandes.kandy.ktx

import android.animation.Animator
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pt.hugofernandes.kandy.ProtectedClickListener

/**
 * Usage example:
 * view.setProtectedClickListener { }
 */
fun View.setProtectedClickListener(onProtectedClick: (View) -> Unit) {
    setOnClickListener(ProtectedClickListener { v ->
        onProtectedClick(v)
    })
}

/**
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

fun View.isVisible() = visibility == View.VISIBLE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.isGone() = visibility == View.GONE

fun <F : Fragment> View.findFragment() = runCatching { FragmentManager.findFragment(this) as F }.getOrNull()