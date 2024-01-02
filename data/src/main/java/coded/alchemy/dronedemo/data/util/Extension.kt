package coded.alchemy.dronedemo.data.util

/**
 * [String] class extension that extracts the text value from speech recognition results.
 * */
fun String.extractTextValue(): String? {
    val regex = "\"text\"[^{]*\"([^\"]*)\"".toRegex()
    val matchResult = regex.find(this)
    return matchResult?.groupValues?.get(1)
}