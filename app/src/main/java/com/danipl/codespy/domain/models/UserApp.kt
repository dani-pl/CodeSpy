package com.danipl.codespy.domain.models

import android.graphics.drawable.Drawable
import com.danipl.codespy.util.Framework

data class UserApp(
    val name: String,
    val packageName: String,
    val framework: Framework,
    val icon: Drawable,
)