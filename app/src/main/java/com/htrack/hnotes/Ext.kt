package com.htrack.hnotes

// Regular expression to check if the string is a URL
const val urlRegexStr = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?\$"
const val coordinateUrlRegexStr =
    "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)\$"

fun String.isUrl(): Boolean {
    val urlRegex = Regex(urlRegexStr)
    return urlRegex.matches(this)
}

fun String.isLocationUrl(): Boolean {
    val urlRegex = Regex(urlRegexStr)
    val coordinateRegex = Regex(coordinateUrlRegexStr)
    return urlRegex.matches(this) || coordinateRegex.matches(this)
}