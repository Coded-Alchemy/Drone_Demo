package coded.alchemy.dronedemo.util

fun Float.formatToTenths(): String {
    val formattedNumber = "%.1f".format(this)
    return formattedNumber.replaceFirst("\\.0$".toRegex(), "")
}

fun Float.formatTenthsAndHundredths(): String {
    val formattedNumber = "%.2f".format(this)
    return formattedNumber.substring(formattedNumber.indexOf('.') + 1)
}

fun String.addPercentSign(): String {
    return "$this%"
}