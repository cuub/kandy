package pt.hugofernandes.kandy.ktx

/**
 * Returns the file extension as a [String] based on the name
 */
fun CharSequence?.getFileExtension(): String {
    return if (isNullOrEmpty()) {
        ""
    } else {
        this!!.substring(this.lastIndexOf("."))
    }
}