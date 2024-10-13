package com.danipl.codespy.domain.models

import android.net.Uri
import com.danipl.codespy.util.Framework

data class UserApp(
    val name: String,
    val packageName: String,
    val framework: Framework,
    val iconUri: Uri,
)