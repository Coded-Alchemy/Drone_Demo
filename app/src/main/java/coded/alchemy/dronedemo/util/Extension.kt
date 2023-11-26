package coded.alchemy.dronedemo.util

fun Float.formatToTenths(): String {
    val formattedNumber = "%.1f".format(this)
    return formattedNumber.replaceFirst("\\.0$".toRegex(), "")
}

fun Float.formatTenthsAndHundredths(): String {
    return when {
        this >= 1.0 -> "100"
        else -> {
            val formattedNumber = "%.2f".format(this)
            formattedNumber.substring(formattedNumber.indexOf('.') + 1)
        }
    }
}

fun String.addPercentSign(): String {
    return "$this%"
}

fun Float.calculateMphFromVelocity(): Float {
    val conversionFactor = 2.23694f
    return this * conversionFactor
}

fun String.addMph(): String {
    return "$this mph"
}
