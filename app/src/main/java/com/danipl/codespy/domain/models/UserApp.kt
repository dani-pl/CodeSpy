package com.danipl.codespy.domain.models

import android.graphics.drawable.Drawable

data class UserApp(
    val name: String,
    val packageName: String,
    val icon: Drawable,
)