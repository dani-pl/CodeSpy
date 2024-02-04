package com.danipl.codespy.domain.models

import com.danipl.codespy.data.models.AppInfoDataModel

fun AppInfoDataModel.toDomain(): AppInfo{
    return AppInfo(
        name = this.name,
        icon = this.icon,
        packageName = this.packageName
    )
}