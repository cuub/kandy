package pt.hugofernandes.kandy.ktx

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri

fun Context.displayWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.displayHeight(): Int {
    return resources.displayMetrics.heightPixels
}

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

fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(getResourcePackageName(resourceId))
        .appendPath(getResourceTypeName(resourceId))
        .appendPath(getResourceEntryName(resourceId))
        .build()
}