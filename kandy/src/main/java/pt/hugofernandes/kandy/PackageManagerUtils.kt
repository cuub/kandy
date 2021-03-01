package pt.hugofernandes.kandy

import android.content.Context

fun isPackageInstalled(context: Context, packageName: String) = runCatching { context.packageManager.getPackageInfo(packageName, 0) }.isSuccess
