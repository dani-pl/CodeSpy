package com.danipl.codespy.util

enum class Framework {
    REACT_NATIVE, CORDOVA, FLUTTER, UNCLASSIFIED
}

fun String.toFramework() = when(this) {
    "REACT_NATIVE" -> Framework.REACT_NATIVE
    "CORDOVA" -> Framework.CORDOVA
    "FLUTTER" -> Framework.FLUTTER
    else -> Framework.UNCLASSIFIED
}