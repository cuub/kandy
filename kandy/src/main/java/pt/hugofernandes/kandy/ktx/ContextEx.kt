package pt.hugofernandes.kandy.ktx

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Returns the display width in pixels
 */
inline fun Context.displayWidth(): Int {
    return resources.displayMetrics.widthPixels
}

/**
 * Returns the display height in pixels
 */
inline fun Context.displayHeight(): Int {
    return resources.displayMetrics.heightPixels
}

/**
 * Returns the [Resources] associated with a given [packageName] or null if [packageName] is not found
 */
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

/**
 * Creates an [Uri] given a [resourceId]
 */
fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(getResourcePackageName(resourceId))
        .appendPath(getResourceTypeName(resourceId))
        .appendPath(getResourceEntryName(resourceId))
        .build()
}

/**
 * Creates and shows a [Toast] with the given [text]
 *
 * @param duration Toast duration, defaults to [Toast.LENGTH_SHORT]
 *
 * Example of usage: toast("bla")
 */
inline fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, text, duration).apply { show() }
}

/**
 * Creates and shows a [Toast] with text from a resource
 *
 * @param resId Resource id of the string resource to use
 * @param duration Toast duration, defaults to [Toast.LENGTH_SHORT]
 */
inline fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, resId, duration).apply { show() }
}

/**
 * Starts the Activity [A]. Allows to configure the [Intent] using the optional [configIntent] lambda.
 *
 * Example of usage: startActivity<BlaActivity>(configIntent = {putExtra("bla", "")})
 */
inline fun <reified A : Activity> Context.startActivity(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}

/**
 * Starts an Activity that resolves [action]. Allows to configure the [Intent] using the optional [configIntent] lambda.
 *
 * If there's no matching [Activity], the underlying platform API will throw an [ActivityNotFoundException].
 *
 * If there is more than one matching [Activity], the Android system may show an activity chooser
 */
@Throws(ActivityNotFoundException::class)
inline fun Context.startActivity(action: String, configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(action).apply(configIntent))
}
