package com.danipl.codespy.data.models

import android.graphics.drawable.Drawable

data class AppInfoDataModel(
    val name: String,
    val packageName: String,
    val icon: Drawable,
)