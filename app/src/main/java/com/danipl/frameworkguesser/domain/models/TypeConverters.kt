package com.danipl.frameworkguesser.domain.models

import com.danipl.frameworkguesser.data.models.AppInfoDataModel

fun AppInfoDataModel.toDomain(): AppInfo{
    return AppInfo(
        name = this.name,
        icon = this.icon,
        packageName = this.packageName
    )
}