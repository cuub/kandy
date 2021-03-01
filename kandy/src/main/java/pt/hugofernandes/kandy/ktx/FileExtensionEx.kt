package pt.hugofernandes.kandy.ktx

fun CharSequence?.getFileExtension(): String {
    return if (isNullOrEmpty()) {
        ""
    } else {
        this!!.substring(this.lastIndexOf("."))
    }
}