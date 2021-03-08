package pt.hugofernandes.kandy

import android.content.Context

/**
 * Checks whether the given package is installed
 * @param context Your context
 * @param packageName the package name to check if it's installed
 */
fun isPackageInstalled(context: Context, packageName: String) = runCatching { context.packageManager.getPackageInfo(packageName, 0) }.isSuccess
