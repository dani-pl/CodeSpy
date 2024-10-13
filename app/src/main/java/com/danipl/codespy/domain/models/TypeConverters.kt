package com.danipl.codespy.domain.models

import androidx.core.net.toUri
import com.danipl.codespy.data.db.UserAppEntity
import com.danipl.codespy.util.toFramework

fun UserAppEntity.toUserApp() = UserApp(
    name = this.name,
    packageName = this.packageName,
    framework = this.framework.toFramework(),
    iconUri = this.iconUri.toUri()
)

fun List<UserAppEntity>.toUserAppList() = this.map {
    it.toUserApp()
}