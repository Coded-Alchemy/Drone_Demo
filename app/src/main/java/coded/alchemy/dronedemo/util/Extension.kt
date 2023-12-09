package coded.alchemy.dronedemo.util

/**
 * Extension.kt
 *
 * This file contains extension functions that provide extra functionality.
 * @author Taji Abdullah
 * */

/**
 * [Float] class extension to provide a [String] that displays a Float stripped
 * of values after the tenths place after the decimal. This is used for displaying altitude.
 * */
fun Float.formatToTenths(): String {
    val formattedNumber = "%.1f".format(this)
    return formattedNumber.replaceFirst("\\.0$".toRegex(), "")
}

/**
 * [Float] class extension to provide a [String] that displays 100 for a value of 1.0.
 * or a Float stripped of values after the hundredths place after the decimal. This is used for
 * displaying battery percentage.
 * */
fun Float.formatToTenthsAndHundredths(): String {
    return when {
        this >= 1.0 -> "100"
        else -> {
            val formattedNumber = "%.2f".format(this)
            formattedNumber.substring(formattedNumber.indexOf('.') + 1)
        }
    }
}

/**
 * [Float] class extension that calculates mph from a GPS velocity reading.
 * */
fun Float.calculateMphFromVelocity(): Float {
    val conversionFactor = 2.23694f
    return this * conversionFactor
}

/**
 * [String] class extension that appends a percent sign.
 * This is used for displaying battery percentage.
 * */
fun String.appendPercentSign(): String {
    return "$this%"
}

/**
 * [String] class extension that appends mph.
 * */
fun String.appendMph(): String {
    return "$this mph"
}

fun String.extractTextValue(): String? {
    val regex = "\"text\"[^{]*\"([^\"]*)\"".toRegex()
    val matchResult = regex.find(this)
    return matchResult?.groupValues?.get(1)
}
